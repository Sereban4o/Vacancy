package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.SearchInputField
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.util.TypeState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Поле ввода текста
        SearchInputField(
            query = uiState.query,
            onTextChanged = viewModel::onQueryChanged,
            onClearClick = { viewModel.onQueryChanged("") }
        )
        if (uiState.query.isEmpty()) {
            InfoState(TypeState.SearchVacancy)
        }

        // Ошибка
        if (uiState.errorType != SearchErrorType.NONE) {
            when (uiState.errorType) {
                SearchErrorType.NETWORK ->
                    InfoState(TypeState.NoInternet)

                else -> {}
            }
        }

        // Количество найденных вакансий
        if (!uiState.isInitial) {
            Text(
                text = "Найдено ${uiState.totalFound} вакансий",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )
            if (
                !uiState.isLoading &&
                uiState.totalFound == 0 &&
                uiState.errorType == SearchErrorType.NONE
            ) {
                InfoState(TypeState.NoDataVacancy)
            }
        }

        // Список вакансий
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = uiState.vacancies,
                key = { it.id }
            ) { vacancy ->
                VacancyItem(
                    vacancy = vacancy,
                    onClick = { onVacancyClick(vacancy.id) }
                )
            }
        }
    }
}
