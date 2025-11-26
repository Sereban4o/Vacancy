package ru.practicum.android.diploma.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.SearchFilters

interface VacanciesRepository {

    suspend fun getVacancyDetails(id: String): VacancyDetails

    fun searchVacanciesPaged(
        query: String,
        filters: SearchFilters?,
        onTotalFound: (Int) -> Unit
    ): Flow<PagingData<Vacancy>>
}
