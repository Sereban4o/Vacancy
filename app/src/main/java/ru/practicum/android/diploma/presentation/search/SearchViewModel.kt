package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.main.SearchErrorType
import ru.practicum.android.diploma.ui.main.SearchUiState

/**
 * ViewModel для экрана поиска вакансий.
 *
 * Принимает сырой текст запроса, применяет debounce
 * и по истечении паузы вызывает доменный interactor.
 */

class SearchViewModel(
    private val searchVacanciesInteractor: SearchVacanciesInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState())

    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _totalFound = MutableStateFlow(0)
    val totalFound: StateFlow<Int> = _totalFound.asStateFlow()

    // Поток для текущего запроса
    private val searchQueryFlow = MutableStateFlow("")

    // Основной поток для ui
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingResultDataFlow: Flow<PagingData<Vacancy>> = searchQueryFlow.debounce(SEARCH_DELAY_MS)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                // Вернём пустой поток при пустом запросе
                _totalFound.value = 0
                kotlinx.coroutines.flow.flow { emit(PagingData.empty<Vacancy>()) }
            } else {
                // Пагинированный поиск через интерактор (пока без фильтров)
                kotlinx.coroutines.flow.flow { emitAll(
                    searchVacanciesInteractor.searchPaged(
                        query = query,
                        filters = null,
                        onTotalFound = { total ->
                            _totalFound.value = total
                        }
                    )
                )
                }
            }
        }.cachedIn(viewModelScope)
    // Кеширование для последующего переиспользования данных (а не выполнения нового запроса заново)

    /**
     * Вызывается из UI при каждом изменении текста в поле поиска.
     * Принимает "сырой" текст, как того требует Issue 3.2.
     */
    fun onQueryChanged(newQuery: String) {
        _uiState.update { current ->
            current.copy(
                query = newQuery,
                isInitial = false
            )
        }

        searchQueryFlow.value = newQuery // Обновление запускает новый поиск

        // Если строка пустая — просто очищаем результаты и НЕ запускаем поиск.
        if (newQuery.isBlank()) {
            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    errorType = SearchErrorType.NONE,
                    totalFound = 0
                )
            }
            return
        } else {
            // Показываем загрузку для нового запроса
            _uiState.update { current ->
                current.copy(
                    isLoading = true,
                    errorType = SearchErrorType.NONE
                )
            }
        }

    }

    // Для обновления данных о количестве найденных вакансий
    fun updateTotalFound(total: Int) {
        _totalFound.value = total
    }

    /**
     * Повторить поиск при ошибке (кнопка "Повторить").
     */
    fun onRetry() {
        val currentQuery: String = _uiState.value.query
        if (currentQuery.isBlank()) return

        // Повторный запрос
        searchQueryFlow.value = currentQuery
    }

    companion object {
        // Константа теперь локальна для ViewModel, без глобального Constants
        private const val SEARCH_DELAY_MS: Long = 2_000L
    }
}
