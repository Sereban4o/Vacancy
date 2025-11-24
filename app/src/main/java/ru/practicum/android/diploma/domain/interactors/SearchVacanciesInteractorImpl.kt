package ru.practicum.android.diploma.domain.interactors

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

class SearchVacanciesInteractorImpl(
    private val vacanciesRepository: VacanciesRepository
) : SearchVacanciesInteractor{

    override suspend fun search(
        query: String,
        page: Int,
        filters: SearchFilters?
    ): VacanciesSearchResult {
        return vacanciesRepository.searchVacancies(
            query = query,
            page = page,
            filters = filters
        )
    }

    // Используем этот метод
    override suspend fun searchPaged(
        query: String,
        filters: SearchFilters?
    ): Flow<PagingData<Vacancy>> {
        return vacanciesRepository.searchVacanciesPaged(
            query = query,
            filters = filters
        )
    }
}
