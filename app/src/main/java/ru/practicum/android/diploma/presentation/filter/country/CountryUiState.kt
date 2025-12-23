package ru.practicum.android.diploma.presentation.filter.country

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.practicum.android.diploma.domain.models.FilterParameter

@Immutable
data class CountryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError: Boolean = false,
    val countries: ImmutableList<FilterParameter> = persistentListOf()
)
