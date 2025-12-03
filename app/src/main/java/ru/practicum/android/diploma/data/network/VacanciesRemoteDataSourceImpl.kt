package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
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

        Log.d("FILTER_CHAIN", "Remote → sending request = $request")

        // обязательные
        params[PARAM_TEXT] = request.text
        params[PARAM_PAGE] = request.page.toString()

        // опциональные — добавляем только если заданы

        request.regionId?.let { regionId ->
            // area в Practicum API
            params[PARAM_AREA] = regionId
        }

        request.industryId?.let { industryId ->
            params[PARAM_INDUSTRY] = industryId
        }

        request.salaryFrom?.let { salary ->
            // Practicum: параметр называется просто "salary"
            params[PARAM_SALARY] = salary.toString()
        }

        if (request.onlyWithSalary) {
            params[PARAM_ONLY_WITH_SALARY] = VALUE_TRUE
        }

        // Никакого per_page, salary_from, salary_to, schedule тут нет

        networkClient.execute {
            searchVacancies(params)
        }
    }

    override suspend fun getVacancyDetails(id: String): VacancyDetailDto =
        withContext(ioDispatcher) {
            networkClient.execute {
                getVacancyDetails(id)
            }
        }

    override suspend fun getAreas(): List<FilterAreaDto> =
        withContext(ioDispatcher) {
            networkClient.execute {
                getAreas()
            }
        }

    override suspend fun getIndustries(): List<FilterIndustryDto> =
        withContext(ioDispatcher) {
            networkClient.execute {
                getIndustries()
            }
        }

    companion object {
        private const val PARAM_TEXT = "text"
        private const val PARAM_PAGE = "page"
        private const val PARAM_AREA = "area"
        private const val PARAM_INDUSTRY = "industry"
        private const val PARAM_SALARY = "salary"
        private const val PARAM_ONLY_WITH_SALARY = "only_with_salary"

        private const val VALUE_TRUE = "true"
    }
}
