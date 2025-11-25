package ru.practicum.android.diploma.data.network

/**
 * Единая точка входа для сетевых вызовов.
 * Через него потом будут ходить все репозитории.
 */
interface NetworkClient {

    /**
     * Обёртка над вызовами ApiService с общей логикой:
     * - проверка интернета
     * - обработка ошибок (добавим позже)
     */
    suspend fun <T> execute(block: suspend VacanciesApiService.() -> T): T
}
