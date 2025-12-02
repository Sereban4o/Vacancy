package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.VacanciesRepository

class VacancyDetailsInteractor(
    private val repository: VacanciesRepository
) {
    suspend fun getVacancyDetails(id: String): VacancyDetails {
        return repository.getVacancyDetails(id)
    }
}
