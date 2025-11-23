package ru.practicum.android.diploma.data.network

/**
 * Интерфейс для проверки состояния сети.
 */
interface NetworkStatusChecker {
    fun isConnected(): Boolean
}
