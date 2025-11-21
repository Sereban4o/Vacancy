package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.util.NetworkUtils
import java.io.IOException

/**
 * Реализация NetworkClient.
 * Сейчас использует заглушку NetworkUtils.isOnline, которая всегда true.
 * Позже заменишь реализацию NetworkUtils на реальную проверку сети.
 */
class NetworkClientImpl(
    private val apiService: ApiService
) : NetworkClient {

    override suspend fun <T> execute(block: suspend ApiService.() -> T): T {
        // Используем заглушку из NetworkUtils
        if (!NetworkUtils.isOnline) {
            // Потом можно заменить на Result/Resource/своё исключение
            throw IOException("No internet connection")
        }

        return block(apiService)
    }
}
