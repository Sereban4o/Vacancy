package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.main.SearchErrorType
import ru.practicum.android.diploma.ui.main.SearchUiState
import java.io.IOException

class SearchViewModel(
    private val searchVacanciesInteractor: SearchVacanciesInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // сырой текст запроса
    private val searchQueryFlow = MutableStateFlow("")

    /**
     * Основной поток PagingData<Vacancy>.
     * Внутри:
     *  - debounce по тексту,
     *  - пустой поток при пустом запросе,
     *  - interactor.searchPaged(...) при нормальном запросе.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingResultDataFlow: Flow<PagingData<Vacancy>> =
        searchQueryFlow
            .flatMapLatest { query ->
                if (query.isNotBlank()) {
                    // Очищаем ui перед новым запросом(ещё до отправки запроса)
                    _uiState.update { current ->
                        current.copy(
                            isLoading = true,
                            errorType = SearchErrorType.NONE,
                            isInitial = false,
                            totalFound = 0
                        )
                    }

                    // Эмитим пустые данные чтобы они из-за кэширования не просачивались через flatMap
                    val clearFlow = flow<PagingData<Vacancy>> {
                        emit(PagingData.empty())
                    }

                    val searchFlow = flow { emit(query) }
                        .debounce(SEARCH_DELAY_MS)
                        .distinctUntilChanged()
                        .flatMapLatest { searchQuery ->
                            searchVacanciesInteractor.searchPaged(
                                query = searchQuery,
                                filters = null,
                                onTotalFound = { total ->
                                    _uiState.update { it.copy(totalFound = total) }
                                }
                            )
                        }

                    // Очистка + поиск
                    clearFlow.flatMapLatest { searchFlow }
                } else {
                    // Пустой запрос
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            errorType = SearchErrorType.NONE,
                            totalFound = 0,
                            isInitial = true
                        )
                    }
                    flow { emit(PagingData.empty()) }
                }
            }
            .cachedIn(viewModelScope)

    /**
     * Пользователь меняет текст в поиске.
     */
    fun onQueryChanged(newQuery: String) {
        _uiState.update { current ->
            current.copy(
                query = newQuery,
                isInitial = false,
            )
        }
        searchQueryFlow.value = newQuery
    }

    /**
     * View сообщает сюда изменения loadState Paging'а.
     * Здесь мы обновляем isLoading + errorType.
     */
    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        val refresh = loadState.refresh
        _uiState.update { current ->
            when (refresh) {
                is LoadState.Loading -> {
                    current.copy(
                        isLoading = true,
                        errorType = SearchErrorType.NONE
                    )
                }

                is LoadState.NotLoading -> {
                    current.copy(
                        isLoading = false
                    )
                }

                is LoadState.Error -> {
                    val errorType = if (refresh.error is IOException) {
                        SearchErrorType.NETWORK
                    } else {
                        SearchErrorType.GENERAL
                    }
                    current.copy(
                        isLoading = false,
                        errorType = errorType
                    )
                }
            }
        }
    }

    /**
     * Повторить поиск при ошибке (если понадобится).
     */
    fun onRetry() {
        val currentQuery = _uiState.value.query
        if (currentQuery.isNotBlank()) {
            searchQueryFlow.value = currentQuery
        }
    }

    companion object {
        private const val SEARCH_DELAY_MS: Long = 2_000L
    }
}
