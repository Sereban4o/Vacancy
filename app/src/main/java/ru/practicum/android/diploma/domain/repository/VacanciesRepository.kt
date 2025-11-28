package ru.practicum.android.diploma.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchFilters
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

/**
 * Репозиторий вакансий — граница между Domain и Data.
 *
 * По методичке:
 * - Domain-слой работает только с интерфейсами репозиториев,
 * - конкретная реализация (сеть / кеш / БД) спрятана в data-слое.
 */
interface VacanciesRepository {

    /**
     * Получение деталей вакансии по ID.
     */
    suspend fun getVacancyDetails(id: String): VacancyDetails

    /**
     * Пагинированный поиск вакансий.
     *
     * @param query       строка поиска.
     * @param filters     доменная модель фильтров.
     * @param onTotalFound колбэк для передачи "found" (общее количество результатов).
     *
     * Возвращает Flow<PagingData<Vacancy>> для работы с Paging 3 и LazyPagingItems в UI.
     */
    fun searchVacanciesPaged(
        query: String,
        filters: SearchFilters?,
        onTotalFound: (Int) -> Unit
    ): Flow<PagingData<Vacancy>>
}
