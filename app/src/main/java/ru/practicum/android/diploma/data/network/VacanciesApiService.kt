package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto

/**
 * Retrofit API для Practicum Vacancies API.
 *
 * baseUrl: https://practicum-diploma-8bc38133faba.herokuapp.com/
 */
interface VacanciesApiService {
    /**
     * Поиск вакансий с фильтрами.
     *
     * GET /vacancies?area=&industry=&text=&salary=&page=&only_with_salary=
     */
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String>
    ): VacancySearchResponseDto

    /**
     * Детали вакансии по ID.
     *
     * GET /vacancies/{id}
     */
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") id: String
    ): VacancyDetailDto

    /**
     * Список регионов.
     *
     * GET /areas
     */
    @GET("areas")
    suspend fun getAreas(): List<FilterAreaDto>

    /**
     * Список отраслей.
     *
     * GET /industries
     */
    @GET("industries")
    suspend fun getIndustries(): List<FilterIndustryDto>
}
