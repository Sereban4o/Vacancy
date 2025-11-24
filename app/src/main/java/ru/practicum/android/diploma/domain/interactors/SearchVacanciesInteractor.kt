package ru.practicum.android.diploma.domain.interactors

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchVacanciesInteractor {

    suspend fun search(
        query: String,
        page: Int = 0,
        filters: SearchFilters? = null
    ): VacanciesSearchResult

    suspend fun searchPaged(
        query: String,
        filters: SearchFilters? = null
    ): Flow<PagingData<Vacancy>>
}
