package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.StubVacancyRepository
import ru.practicum.android.diploma.domain.models.StubVacancy

/**
 * Заглушка интерактора.
 * Реальную бизнес-логику добавим позже.
 */
class VacancyInteractorImpl(
    private val repository: StubVacancyRepository
) {

    suspend fun getStubVacancies(): List<StubVacancy> {
        return repository.getStubVacancies()
    }
}
