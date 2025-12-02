package ru.practicum.android.diploma.presentation.workplace

import ru.practicum.android.diploma.domain.models.FilterParameter

data class WorkPlaceUiState(
    val country: FilterParameter? = null,
    val region: FilterParameter? = null
) {
    val hasSelection: Boolean
        get() = country != null || region != null
}
