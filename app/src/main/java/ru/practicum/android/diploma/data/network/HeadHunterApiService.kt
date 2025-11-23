package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto

/**
 * Retrofit API для поиска вакансий на HH.
 *
 * Базовый URL (в Retrofit): https://api.hh.ru/
 * Метод: GET /vacancies
 */
interface HeadHunterApiService {

    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String>
    ): VacancySearchResponseDto
}
