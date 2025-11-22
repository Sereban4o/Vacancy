package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.SearchVacanciesInteractor
import ru.practicum.android.diploma.util.Constants.Debounce.SEARCH_DELAY_MS

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
     * Job для текущего debounce-запроса.
     * Каждый новый ввод текста отменяет предыдущий job.
     */
    private var searchJob: Job? = null

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

        // Если строка пустая — просто очищаем результаты и отменяем запросы.
        if (newQuery.isBlank()) {
            searchJob?.cancel()
            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    vacancies = emptyList(),
                    errorMessage = null
                )
            }
            return
        }

        // Debounce: отменяем предыдущий и запускаем новый с задержкой.
        launchDebouncedSearch(newQuery)
    }

    /**
     * Позволяет повторить поиск при ошибке (кнопка "Повторить").
     */
    fun onRetry() {
        val currentQuery: String = _uiState.value.query
        if (currentQuery.isBlank()) return

        launchDebouncedSearch(currentQuery)
    }

    private fun launchDebouncedSearch(query: String) {
        // 1. отменяем предыдущий job (AC: повторные вызовы отменяются)
        searchJob?.cancel()

        // 2. запускаем новый debounce job
        searchJob = viewModelScope.launch {
            // задержка из константы (AC: 2 секунды)
            delay(SEARCH_DELAY_MS)

            // 3. после паузы запускаем реальный поиск
            performSearch(query)
        }
    }

    private suspend fun performSearch(query: String) {
        // loading
        _uiState.update { current ->
            current.copy(
                isLoading = true,
                errorMessage = null
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
                    errorMessage = null
                )
            }
        } catch (io: IOException) {
            // Ошибка сети
            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    errorMessage = "Проверьте подключение к интернету"
                )
            }
        } catch (e: Exception) {
            // Любая другая ошибка
            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    errorMessage = "Произошла ошибка"
                )
            }
        }
    }
}
