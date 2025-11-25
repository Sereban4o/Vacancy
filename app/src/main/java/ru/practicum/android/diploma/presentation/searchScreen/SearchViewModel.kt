package ru.practicum.android.diploma.presentation.searchScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.ui.main.SearchErrorType
import ru.practicum.android.diploma.ui.main.SearchUiState
import ru.practicum.android.diploma.util.debounce
import java.io.IOException

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

    /**
     * Debounce-функция, которая будет вызываться при изменении запроса.
     * Внутри неё, через задержку, вызывается performSearch().
     */
    private val searchDebounce = debounce<String>(
        coroutineScope = viewModelScope,
        delayMs = SEARCH_DELAY_MS
    ) { query ->
        performSearch(query)
    }

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

        // Если строка пустая — просто очищаем результаты и НЕ запускаем поиск.
        if (newQuery.isBlank()) {
            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    vacancies = emptyList(),
                    errorType = SearchErrorType.NONE
                )
            }
            return
        }

        // Debounce-поиск
        searchDebounce(newQuery)
    }

    /**
     * Повторить поиск при ошибке (кнопка "Повторить").
     */
    fun onRetry() {
        val currentQuery: String = _uiState.value.query
        if (currentQuery.isBlank()) return

        // Повторный запуск через тот же debounce
        searchDebounce(currentQuery)
    }

    private suspend fun performSearch(query: String) {
        // loading
        _uiState.update { current ->
            current.copy(
                isLoading = true,
                errorType = SearchErrorType.NONE
            )
        }

        try {
            val result = searchVacanciesInteractor.search(
                query = query,
                page = 0,
                filters = null
            )

            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    vacancies = result.vacancies,
                    errorType = SearchErrorType.NONE,
                    totalFound = result.found
                )
            }
        } catch (io: IOException) {
            Log.e("SearchViewModel", "Network error while searching vacancies", io)

            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    errorType = SearchErrorType.NETWORK
                )
            }
        }
    }

    companion object {
        // Константа теперь локальна для ViewModel, без глобального Constants
        private const val SEARCH_DELAY_MS: Long = 2_000L
    }
}
