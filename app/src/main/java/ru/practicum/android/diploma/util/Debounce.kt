package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Универсальная debounce-функция.
 *
 * Пример использования в ViewModel:
 *
 * private val searchDebounce = debounce<String>(
 *     coroutineScope = viewModelScope,
 *     delayMs = AppConstants.Debounce.SEARCH_DELAY_MS
 * ) { query ->
 *     performSearch(query)
 * }
 *
 * fun onSearchQueryChanged(query: String) {
 *     searchDebounce(query)
 * }
 */
fun <T> debounce(
    coroutineScope: CoroutineScope,
    delayMs: Long = Constants.Debounce.SEARCH_DELAY_MS,
    onDebounced: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null

    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(delayMs)
            onDebounced(param)
        }
    }
}
