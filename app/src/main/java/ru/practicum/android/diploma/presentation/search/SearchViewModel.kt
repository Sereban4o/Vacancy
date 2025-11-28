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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.main.SearchErrorType
import ru.practicum.android.diploma.ui.main.SearchUiState
import java.io.IOException

/**
 * ViewModel для экрана поиска вакансий с пагинацией (Paging 3).
 */
class SearchViewModel(
    private val searchVacanciesInteractor: SearchVacanciesInteractor
) : ViewModel() {

    // 🔹 UI-состояние
    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // 🔹 Текущий поисковый запрос (сырой текст)
    private val searchQueryFlow = MutableStateFlow("")

    /**
     * Основной поток данных для UI.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingResultDataFlow: Flow<PagingData<Vacancy>> =
        searchQueryFlow
            .debounce(SEARCH_DELAY_MS)
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    // Пустой запрос → очищаем состояние и отдаём пустой список
                    _uiState.update { current ->
                        current.copy(
                            isInitial = true,
                            isLoading = false,
                            errorType = SearchErrorType.NONE,
                            totalFound = 0
                        )
                    }
                    flowOf(PagingData.empty())
                } else {
                    // Новый запрос → выходим из initial и показываем загрузку
                    _uiState.update { current ->
                        current.copy(
                            isInitial = false,
                            isLoading = true,
                            errorType = SearchErrorType.NONE
                        )
                    }

                    // Пагинированный поиск через интерактор (без фильтров пока)
                    searchVacanciesInteractor.searchPaged(
                        query = query,
                        filters = null,
                        onTotalFound = { total ->
                            _uiState.update { state ->
                                state.copy(totalFound = total)
                            }
                        }
                    )
                }
            }
            .cachedIn(viewModelScope)

    /**
     * Вызывается из UI при каждом изменении текста в поле поиска.
     */
    fun onQueryChanged(newQuery: String) {
        // Обновляем только текст (остальное — в потоках выше)
        _uiState.update { current ->
            current.copy(query = newQuery)
        }

        if (newQuery.isBlank()) {
            // Пустая строка → возвращаемся к начальному экрану с плейсхолдером
            searchQueryFlow.value = ""
            _uiState.update { current ->
                current.copy(
                    isInitial = true,
                    isLoading = false,
                    errorType = SearchErrorType.NONE,
                    totalFound = 0
                )
            }
        } else {
            // Непустой текст → триггерим дебаунс-поиск
            searchQueryFlow.value = newQuery
        }
    }

    /**
     * Обработка состояний загрузки Paging 3.
     *
     * Вызывается из UI через LaunchedEffect в SearchScreen.
     */
    fun onLoadStateChanged(loadStates: CombinedLoadStates) {
        val refreshState = loadStates.refresh

        _uiState.update { current ->
            when (refreshState) {
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
                    current.copy(
                        isLoading = false,
                        errorType = mapThrowableToErrorType(refreshState.error)
                    )
                }
            }
        }
    }

    /**
     * Повторить поиск при ошибке (если будет кнопка "Повторить").
     */
    fun onRetry() {
        val currentQuery = _uiState.value.query
        if (currentQuery.isBlank()) return

        // Просто перезапускаем запрос для того же текста:
        searchQueryFlow.value = currentQuery
    }

    private fun mapThrowableToErrorType(throwable: Throwable): SearchErrorType =
        when (throwable) {
            is IOException -> SearchErrorType.NETWORK
            else -> SearchErrorType.GENERAL
        }

    companion object {
        // Задержка дебаунса (2 сек) из условия эпика
        private const val SEARCH_DELAY_MS: Long = 2_000L
    }
}
