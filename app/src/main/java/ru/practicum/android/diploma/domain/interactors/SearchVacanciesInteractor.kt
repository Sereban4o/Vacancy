package ru.practicum.android.diploma.domain.interactors

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy

/**
 * Interactor для сценария "поиск вакансий".
 *
 * По методичке Практикума:
 * - слой Domain не знает деталей сети / Retrofit;
 * - всё общение с данными идет через Repository;
 * - Presentation-слой работает только с Interactor'ом.
 */
interface SearchVacanciesInteractor {

    /**
     * Пагинированный поиск вакансий.
     *
     * @param query    строка поиска (raw text из ViewModel)
     * @param filters  доменная модель фильтров (зарплата, регион, индустрия и т.д.)
     * @param onTotalFound колбэк для передачи общего числа найденных вакансий (found из API).
     *
     * Возвращает Flow<PagingData<Vacancy>>, как рекомендует Paging 3 + Compose.
     */
    fun searchPaged(
        query: String,
        filters: SearchFilters? = null,
        onTotalFound: (Int) -> Unit
    ): Flow<PagingData<Vacancy>>
}
