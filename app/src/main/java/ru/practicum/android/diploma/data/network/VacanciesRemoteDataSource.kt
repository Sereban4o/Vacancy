package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancySearchRequestDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto

/**
 * Источник данных для поиска вакансий (удалённый API).
 */
interface VacanciesRemoteDataSource {

    suspend fun searchVacancies(
        request: VacancySearchRequestDto
    ): VacancySearchResponseDto

    suspend fun getVacancyDetails(id: String): VacancyDetailDto
    suspend fun getAreas(): List<FilterAreaDto>
    suspend fun getIndustries(): List<FilterIndustryDto>
}
