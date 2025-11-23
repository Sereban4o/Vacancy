package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET

/**
 * Базовый ApiService.
 * Позже сюда добавим реальные запросы (поиск вакансий, детали и т.д.).
 */
interface ApiService {

    // Тестовый запрос, чтобы проверить, что Retrofit живой.
    @GET("/")
    suspend fun ping(): Response<Unit>
}
