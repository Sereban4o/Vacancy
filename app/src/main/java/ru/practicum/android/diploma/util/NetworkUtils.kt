package ru.practicum.android.diploma.util

/**
 * Заглушка утилиты для работы с сетью.
 */
object NetworkUtils {
    // Временная константа
    const val DEFAULT_ONLINE = true

    val isOnline: Boolean
        get() = DEFAULT_ONLINE
}
