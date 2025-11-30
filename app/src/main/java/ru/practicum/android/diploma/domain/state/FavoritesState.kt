package ru.practicum.android.diploma.domain.state

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface FavoritesState {

    object Loading : FavoritesState

    data class Content(
        val vacancy: List<VacancyDetails>
    ) : FavoritesState

    object Empty : FavoritesState

    object Error : FavoritesState
}
