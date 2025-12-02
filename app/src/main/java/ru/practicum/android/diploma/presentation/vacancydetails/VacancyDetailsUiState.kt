package ru.practicum.android.diploma.presentation.vacancydetails

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface VacancyDetailsUiState {
    object Loading : VacancyDetailsUiState
    data class Content(
        val vacancy: VacancyDetails,
        val isFavorite: Boolean,
        val descriptionItems: List<DescriptionItem>
    ) : VacancyDetailsUiState
    data class Error(val isNetworkError: Boolean) : VacancyDetailsUiState
    data object NoVacancy : VacancyDetailsUiState
}
