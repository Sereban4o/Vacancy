package ru.practicum.android.diploma.data.repository


import ru.practicum.android.diploma.data.dto.VacancySearchRequestDto
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.mappers.toDomainDetails
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

class VacanciesRepositoryImpl(
    private val remoteDataSource: VacanciesRemoteDataSource
) : VacanciesRepository {

    override suspend fun searchVacancies(
        query: String,
        page: Int,
        filters: SearchFilters?
    ): VacanciesSearchResult {

        // ТРЕБОВАНИЕ ТЗ: пока фиксируем размер страницы 20 элементов
        val perPage = 20

        val requestDto = VacancySearchRequestDto(
            text = query,
            page = page,
            perPage = perPage,
            salaryFrom = filters?.salaryFrom,
            onlyWithSalary = filters?.onlyWithSalary ?: false,
            regionId = filters?.regionId,
            industryId = filters?.industryId,
        )

        val responseDto = remoteDataSource.searchVacancies(requestDto)

        return responseDto.toDomain()
    }



    override suspend fun getVacancyDetails(id: String): VacancyDetails {
        val dto = remoteDataSource.getVacancyDetails(id)
        return dto.toDomainDetails()
    }
}
