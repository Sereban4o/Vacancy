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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.searchScreen.SearchViewModel
import ru.practicum.android.diploma.ui.components.VacancyItem

/**
 * Входная точка экрана поиска.
 * Здесь работаем с ViewModel и подписываемся на uiState.
 */
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.collectAsState().value

    SearchContent(
        uiState = state,
        onVacancyClick = onVacancyClick,
        modifier = modifier
    )
}

/**
 * Чистый UI-компонент, который ничего не знает о ViewModel.
 * Принимает готовый uiState и колбэк клика по вакансии.
 */
@Composable
fun SearchContent(
    uiState: SearchUiState,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // текст ошибки, если она есть
    val errorText: String? = when (uiState.errorType) {
        SearchErrorType.NONE -> null
        SearchErrorType.NETWORK -> stringResource(R.string.error_network)
        SearchErrorType.GENERAL -> stringResource(R.string.error_general)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Ошибка (если есть)
        if (errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Кол-во найденных (только если это уже не самый первый экран)
        if (!uiState.isInitial) {
            Text(
                text = "Найдено ${uiState.totalFound} вакансий",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        // NOTE: сюда добавишь поле ввода запроса (TextField), когда будешь верстать поиск

        // Список вакансий
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = uiState.vacancies,
                key = { it.id } // стабильный ключ — важен для "стабильного отображения"
            ) { vacancy ->
                VacancyItem(
                    vacancy = vacancy,
                    onClick = { onVacancyClick(vacancy.id) }
                )
            }
        }
    }
}
