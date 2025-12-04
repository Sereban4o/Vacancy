package ru.practicum.android.diploma.presentation.filter.industry

import ru.practicum.android.diploma.domain.models.FilterParameter

data class IndustryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val query: String = "",
    val industries: List<FilterParameter> = emptyList(), // отфильтрованный список
    val selectedIndustryId: String? = null // id выбранной отрасли
)
