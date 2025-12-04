package ru.practicum.android.diploma.presentation.filter.region

import ru.practicum.android.diploma.domain.models.FilterParameter

data class RegionUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false, // "Не удалось получить список"
    val query: String = "",
    val regions: List<FilterParameter> = emptyList(),
    val isEmptySearchResult: Boolean = false // "Такого региона нет"
)
