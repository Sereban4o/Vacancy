package ru.practicum.android.diploma.domain.interactors

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

/**
 * Реализация Interactor'а для поиска вакансий.
 *
 * Важно: Interactor НЕ лезет в сеть напрямую, а работает через VacanciesRepository,
 * как и описано в архитектурных рекомендациях Практикума (Domain не знает про data-слой).
 */
class SearchVacanciesInteractorImpl(
    private val vacanciesRepository: VacanciesRepository
) : SearchVacanciesInteractor {

    /**
     * Пагинированный поиск.
     *
     * Здесь мы просто прокидываем запрос в Repository.
     * Вся конкретная логика Paging (Pager, PagingSource) живёт в data-слое,
     * что соблюдает разделение ответственности.
     */
    override fun searchPaged(
        query: String,
        filters: SearchFilters?,
        onTotalFound: (Int) -> Unit
    ): Flow<PagingData<Vacancy>> {
        return vacanciesRepository.searchVacanciesPaged(
            query = query,
            filters = filters,
            onTotalFound = onTotalFound
        )
    }
}
