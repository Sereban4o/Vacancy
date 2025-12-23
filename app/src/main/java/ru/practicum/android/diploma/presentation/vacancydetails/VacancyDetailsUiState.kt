package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import ru.practicum.android.diploma.domain.models.VacancyDetails

@Immutable
sealed interface VacancyDetailsUiState {

    @Immutable
    data object Loading : VacancyDetailsUiState

    @Immutable
    data class Content(
        val vacancy: VacancyDetails,
        val isFavorite: Boolean,
        val descriptionItems: ImmutableList<DescriptionItem>
    ) : VacancyDetailsUiState

    @Immutable
    data class Error(val isNetworkError: Boolean) : VacancyDetailsUiState

    @Immutable
    data object NoVacancy : VacancyDetailsUiState
}
