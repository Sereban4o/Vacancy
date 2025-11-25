package ru.practicum.android.diploma.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.VacancySearchRequestDto
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.mappers.toDomainDetails
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.data.paging.VacanciesPagingSource
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

// требование ТЗ: страница = 20 элементов
private const val VACANCIES_PER_PAGE = 20

class VacanciesRepositoryImpl(
    private val remoteDataSource: VacanciesRemoteDataSource
) : VacanciesRepository {

    // Не уверен нужен ли нам этот метод, если поиск осуществляется с пагинацией, но пока удалять не буду
    override suspend fun searchVacancies(
        query: String,
        page: Int,
        filters: SearchFilters?
    ): VacanciesSearchResult {
        val requestDto = VacancySearchRequestDto(
            text = query,
            page = page,
            perPage = VACANCIES_PER_PAGE,
            salaryFrom = filters?.salaryFrom,
            onlyWithSalary = filters?.onlyWithSalary ?: false,
            regionId = filters?.regionId,
            industryId = filters?.industryId,
        )

        val responseDto = remoteDataSource.searchVacancies(requestDto)
        return responseDto.toDomain()
    }

    override fun searchVacanciesPaged(
        query: String,
        filters: SearchFilters?
    ): Flow<PagingData<Vacancy>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                enablePlaceholders = false // Отвечает за то, прогружать ли ещё незагруженный (пустой) список
            ),
            pagingSourceFactory = {
                VacanciesPagingSource(
                    remoteDataSource = remoteDataSource,
                    query = query,
                    filters = filters
                )
            },
        ).flow
    }

    override suspend fun getVacancyDetails(id: String): VacancyDetails {
        val dto = remoteDataSource.getVacancyDetails(id)
        return dto.toDomainDetails()
    }
}
