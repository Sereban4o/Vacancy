package ru.practicum.android.diploma.presentation.vacancydetails

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface VacancyDetailsUiState {
    object Loading : VacancyDetailsUiState
    data class Content(val vacancy: VacancyDetails) : VacancyDetailsUiState
    data class Error(val isNetworkError: Boolean) : VacancyDetailsUiState

    // отдельный кейс: вакансия не найдена / удалена
    data object NoVacancy : VacancyDetailsUiState
}
