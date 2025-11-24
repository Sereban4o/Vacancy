package ru.practicum.android.diploma.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesRepository {

    suspend fun searchVacancies(
        query: String,
        page: Int,
        filters: SearchFilters? = null
    ): VacanciesSearchResult

    fun searchVacanciesPaged(
        query: String,
        filters: SearchFilters? = null
    ): Flow<PagingData<Vacancy>>
}
