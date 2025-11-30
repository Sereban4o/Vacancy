package ru.practicum.android.diploma.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.state.FavoritesState
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.ui.components.CenteredProgress
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.util.TypeState

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel,
    onVacancyClick: (String) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val chipHeightState = remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Heading(
                text = stringResource(R.string.favorites),
                modifier = modifier
            )
            when (val currentState = state.value) {
                is FavoritesState.Loading -> {
                    CenteredProgress(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                is FavoritesState.Empty -> {
                    InfoState(state = TypeState.EmptyList)
                }
                is FavoritesState.Content -> {
                    HistoryVacancyList(
                        historyData = currentState,
                        topPadding = chipHeightState.value + 16.dp,
                        onVacancyClick = onVacancyClick
                    )
                }
                is FavoritesState.Error -> {
                    InfoState(state = TypeState.NoDataVacancy)
                }
            }

        }
    }
}

@Composable
private fun HistoryVacancyList(
    historyData: FavoritesState.Content,
    topPadding: Dp,
    onVacancyClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = topPadding,
            bottom = 16.dp
        )
    ) {
        items(
            count = historyData.vacancy.size,
            key = { index -> historyData.vacancy[index].id }
        ) { index ->
            val vacancyDetails = historyData.vacancy[index]
            val vacancy = Vacancy(
                id = vacancyDetails.id,
                title = vacancyDetails.title,
                company = vacancyDetails.companyName,
                logoUrl = vacancyDetails.logoUrl,
                salaryFrom = vacancyDetails.salaryFrom,
                salaryTo = vacancyDetails.salaryTo,
                currency = vacancyDetails.currency,
                city = vacancyDetails.region
            )
            VacancyItem(
                vacancy = vacancy,
                onClick = { onVacancyClick(vacancy.id) }
            )
        }
    }
}
