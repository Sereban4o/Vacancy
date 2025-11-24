package ru.practicum.android.diploma.data.paging

import android.util.Log
import androidx.compose.runtime.key
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.dto.VacancySearchRequestDto
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.VacanciesRemoteDataSource
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesPagingSource(
    private val remoteDataSource: VacanciesRemoteDataSource,
    private val query: String,
    private val filters: SearchFilters? = null
) : PagingSource<Int, Vacancy>() {

    // Функция чтобы при обновлении пользователь не терял свою позицию
    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        // Вычисляем ближайшую позицию к "якорю"
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // Функция для загрузки вакансий с пагинацией
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        return try {
            val currentPage = params.key ?: 0

            val requestDto = VacancySearchRequestDto(
                text = query,
                page = currentPage,
                perPage = params.loadSize,
                salaryFrom = filters?.salaryFrom,
                onlyWithSalary = filters?.onlyWithSalary ?: false,
                regionId = filters?.regionId,
                industryId = filters?.industryId
            )

            val response = remoteDataSource.searchVacancies(requestDto)

            val vacancies = response.vacancies.map { it.toDomain() }


            val prevPage = if(currentPage > 0) currentPage - 1 else null
            val nextPage = if(currentPage < response.pages) currentPage+1 else null

            LoadResult.Page(
                data = vacancies,
                prevKey =prevPage,
                nextKey = nextPage
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}
