package ru.practicum.android.diploma.presentation.filter.region

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.practicum.android.diploma.domain.models.FilterParameter

@Immutable
data class RegionUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false, // "Не удалось получить список"
    val query: String = "",
    val regions: ImmutableList<FilterParameter> = persistentListOf(),
    val isEmptySearchResult: Boolean = false // "Такого региона нет"
)
