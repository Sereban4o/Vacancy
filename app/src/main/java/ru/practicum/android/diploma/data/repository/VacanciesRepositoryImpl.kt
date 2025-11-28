package ru.practicum.android.diploma.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.mappers.toDomainDetails
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.data.paging.VacanciesPagingSource
import ru.practicum.android.diploma.data.paging.VACANCIES_PER_PAGE
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

class VacanciesRepositoryImpl(
    private val remoteDataSource: VacanciesRemoteDataSource
) : VacanciesRepository {

    override fun searchVacanciesPaged(
        query: String,
        filters: SearchFilters?,
        onTotalFound: (Int) -> Unit
    ): Flow<PagingData<Vacancy>> {
        return Pager(
            config = PagingConfig(
                pageSize = VACANCIES_PER_PAGE,
                initialLoadSize = VACANCIES_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                VacanciesPagingSource(
                    remoteDataSource = remoteDataSource,
                    query = query,
                    filters = filters,
                    onTotalFound = onTotalFound
                )
            },
        ).flow
    }

    override suspend fun getVacancyDetails(id: String): VacancyDetails {
        val dto = remoteDataSource.getVacancyDetails(id)
        return dto.toDomainDetails()
    }
}
