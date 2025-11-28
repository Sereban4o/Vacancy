package ru.practicum.android.diploma.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.VacancySearchRequestDto
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy
import java.io.IOException

class VacanciesPagingSource(
    private val remoteDataSource: VacanciesRemoteDataSource,
    private val query: String,
    private val filters: SearchFilters? = null,
    private val onTotalFound: (Int) -> Unit = {}
) : PagingSource<Int, Vacancy>() {

    // Функция чтобы при обновлении пользователь не терял свою позицию
    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        // Вычисляем ближайшую позицию к "якорю"
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    // Функция для загрузки вакансий с пагинацией
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        return try {
            val currentPage = params.key ?: 0

            val requestDto = VacancySearchRequestDto(
                text = query,
                page = currentPage,
                perPage = ITEMS_PER_PAGE,
                salaryFrom = filters?.salaryFrom,
                onlyWithSalary = filters?.onlyWithSalary ?: false,
                regionId = filters?.regionId,
                industryId = filters?.industryId
            )

            val response = remoteDataSource.searchVacancies(requestDto)

            val vacancies = response.vacancies.map { it.toDomain() }

            val prevPage = if (currentPage > 0) currentPage - 1 else null
            val lastIndexedPage = response.pages - 1
            val nextPage = if (currentPage < lastIndexedPage) currentPage + 1 else null

            if (currentPage == 0) {
                onTotalFound(response.found)
            }

            LoadResult.Page(
                data = vacancies,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val ITEMS_PER_PAGE = 20
    }
}
