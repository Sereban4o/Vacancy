package ru.practicum.android.diploma.domain.interactors

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchVacanciesInteractor {

    suspend fun searchPaged(
        query: String,
        filters: SearchFilters? = null,
        onTotalFound : (Int) -> Unit
    ): Flow<PagingData<Vacancy>>
}
