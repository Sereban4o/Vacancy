package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET

/**
 * Базовый ApiService.
 * Потом сюда добавим реальные запросы (поиск вакансий, детали и т.п.).
 */
interface ApiService {

    // Тестовый запрос, просто чтобы проверить, что Retrofit живой.
    @GET("/")
    suspend fun ping(): Response<Unit>
}
