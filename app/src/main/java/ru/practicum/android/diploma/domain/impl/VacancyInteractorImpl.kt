package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.StubVacancyRepository
import ru.practicum.android.diploma.domain.models.StubVacancy

/**
 * Базовый интерактор для работы с вакансиями.
 * Сейчас просто пробрасывает вызов в StubVacancyRepository.
 * Позже сюда можно добавить бизнес-логику (фильтрация, маппинг и т.д.).
 */
class VacancyInteractorImpl(
    private val repository: StubVacancyRepository
) {

    suspend fun getStubVacancies(): List<StubVacancy> {
        return repository.getStubVacancies()
    }
}
