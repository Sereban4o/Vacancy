package ru.practicum.android.diploma.presentation.filter.workplace

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.models.FilterParameter

@Immutable
data class WorkPlaceUiState(
    val country: FilterParameter? = null,
    val region: FilterParameter? = null
) {
    val hasSelection: Boolean
        get() = country != null || region != null
}
