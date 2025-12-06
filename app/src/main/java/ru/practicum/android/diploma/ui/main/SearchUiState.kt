package ru.practicum.android.diploma.ui.main

import androidx.compose.runtime.Immutable

/**
 * UI-состояние экрана поиска вакансий.
 */
@Immutable
data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val errorType: SearchErrorType = SearchErrorType.NONE,
    val totalFound: Int = 0,
    val isInitial: Boolean = true,
    val hasActiveFilter: Boolean = false,
)
