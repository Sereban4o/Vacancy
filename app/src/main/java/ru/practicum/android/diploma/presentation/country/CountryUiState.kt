package ru.practicum.android.diploma.presentation.country

import ru.practicum.android.diploma.domain.models.FilterParameter

data class CountryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError: Boolean = false,
    val countries: List<FilterParameter> = emptyList()
)
