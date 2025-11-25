package ru.practicum.android.diploma.data.network

import java.io.IOException

/**
 * Реализация NetworkClient.
 * Перед любым запросом проверяет состояние сети через NetworkStatusChecker.
 */
class NetworkClientImpl(
    private val apiService: VacanciesApiService,
    private val networkStatusChecker: NetworkStatusChecker
) : NetworkClient {

    override suspend fun <T> execute(block: suspend VacanciesApiService.() -> T): T {
        if (!networkStatusChecker.isConnected()) {
            throw IOException("No internet connection")
        }

        return block(apiService)
    }
}
