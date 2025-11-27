package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
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

    val pagedData = viewModel.pagingResultDataFlow.collectAsLazyPagingItems()
    val totalFound = viewModel.totalFound.collectAsState().value

    LaunchedEffect(Unit) {
        // Принудительно запускаем обновление при первом рендере (без этого ищет только со 2 раза)
        if (uiState.query.isNotBlank() && pagedData.itemCount == 0) {
            pagedData.retry()
        }
    }

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
        } else {
            when (pagedData.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is LoadState.Error -> {
                    val error = (pagedData.loadState.refresh as LoadState.Error).error

                    when (error) {
                        is java.io.IOException -> InfoState(TypeState.NoInternet)
                        is retrofit2.HttpException -> InfoState(TypeState.ServerError)
                        else -> {
                            InfoState(TypeState.ServerErrorVacancy)
                        }
                    }
                }
                is LoadState.NotLoading -> {
                    // Заголовок
                    ShowTotalVacancyNumber(uiState, totalFound)
                    // Если вакансий не нашлось
                    if (pagedData.itemCount == 0 && uiState.query.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            InfoState(TypeState.NoDataVacancy)
                        }
                    } else {
                        // Список вакансий
                        CreateVacancyList(pagedData, onVacancyClick)
                    }
                }

            }
        }
    }

}

// Так потом наверное проще будет переделывать
@Composable
private fun ShowTotalVacancyNumber(uiState: SearchUiState, totalFound: Int) {
    // Количество найденных вакансий
    if (!uiState.isInitial) {
        Text(
            text = stringResource(R.string.found_vacancies, totalFound),
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
}

@Composable
private fun CreateVacancyList(
    pagedData: LazyPagingItems<Vacancy>,
    onVacancyClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = pagedData.itemCount,
            key = { index -> pagedData[index]?.id ?: index }
        ) { index ->
            val vacancy = pagedData[index]
            if (vacancy != null) {
                VacancyItem(
                    vacancy = vacancy,
                    onClick = { onVacancyClick(vacancy.id) }
                )
            }
        }

        if (pagedData.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

