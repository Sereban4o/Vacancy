package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.VacancySearchRequestDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto

/**
 * RemoteDataSource, который ходит в Practicum Vacancies API на IO-диспетчере.
 */
class VacanciesRemoteDataSourceImpl(
    private val networkClient: NetworkClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : VacanciesRemoteDataSource {

    override suspend fun searchVacancies(
        request: VacancySearchRequestDto
    ): VacancySearchResponseDto = withContext(ioDispatcher) {

        val params = mutableMapOf<String, String>()

        // обязательные
        params["text"] = request.text
        params["page"] = request.page.toString()

        // опциональные — добавляем только если заданы
        request.regionId?.let { regionId ->
            // area в Practicum API
            params["area"] = regionId
        }

        request.industryId?.let { industryId ->
            params["industry"] = industryId
        }

        request.salaryFrom?.let { salary ->
            // Practicum: параметр называется просто "salary"
            params["salary"] = salary.toString()
        }

        if (request.onlyWithSalary) {
            params["only_with_salary"] = "true"
        }

        // НИКАКОГО per_page, salary_from, salary_to, schedule тут больше нет

        networkClient.execute {
            searchVacancies(params)
        }
    }
}
