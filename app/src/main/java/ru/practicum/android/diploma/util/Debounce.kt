package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Универсальная debounce-функция.
 */
fun <T> debounce(
    coroutineScope: CoroutineScope,
    delayMs: Long,
    onDebounced: suspend (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null

    return { param: T ->
        debounceJob?.cancel() // 🔹 Повторные вызовы отменяются
        debounceJob = coroutineScope.launch {
            delay(delayMs) // 🔹 2 секунды ожидания
            onDebounced(param)
        }
    }
}
