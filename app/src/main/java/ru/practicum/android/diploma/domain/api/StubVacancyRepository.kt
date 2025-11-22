package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.StubVacancy

/**
 * Заглушка интерфейса репозитория.
 * Позже тут будет реальный контракт для работы с вакансиями.
 */
interface StubVacancyRepository {
    suspend fun getStubVacancies(): List<StubVacancy>
}
