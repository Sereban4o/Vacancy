package ru.practicum.android.diploma.presentation.filter.industry

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.practicum.android.diploma.domain.models.FilterParameter

@Immutable
data class IndustryUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val query: String = "",
    val industries: ImmutableList<FilterParameter> = persistentListOf(), // отфильтрованный список
    val selectedIndustryId: String? = null // id выбранной отрасли
)
