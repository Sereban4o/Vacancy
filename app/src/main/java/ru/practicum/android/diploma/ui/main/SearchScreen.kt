package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.components.SearchCountChip
import ru.practicum.android.diploma.ui.components.SearchInputField
import ru.practicum.android.diploma.ui.components.VacancyItem

private const val SEARCH_PLACEHOLDER_WIDTH_FRACTION = 0.9f
private const val SEARCH_PLACEHOLDER_ASPECT_RATIO_WIDTH = 328f
private const val SEARCH_PLACEHOLDER_ASPECT_RATIO_HEIGHT = 223f
private const val SEARCH_PLACEHOLDER_ASPECT_RATIO =
    SEARCH_PLACEHOLDER_ASPECT_RATIO_WIDTH / SEARCH_PLACEHOLDER_ASPECT_RATIO_HEIGHT

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value

    val noResults = !uiState.isInitial &&
        !uiState.isLoading &&
        uiState.errorType == SearchErrorType.NONE &&
        uiState.vacancies.isEmpty()

    val density = LocalDensity.current
    val chipExtraOffset = 5.dp // расстояние от низа поля до чипа
    val chipTopOffsetState = remember { mutableStateOf(0.dp) }
    val chipHeightState = remember { mutableStateOf(0.dp) }

    // 🔹 Внешний Box, чтобы можно было положить чип поверх остального контента
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // ← #FDFDFD / #1A1B22
    ) {
        // Основной контент: поле поиска + плейсхолдер / список / ошибки
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ⬇️ Оборачиваем поле ввода, чтобы измерить его высоту
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        val heightPx = coordinates.size.height.toFloat()
                        chipTopOffsetState.value =
                            with(density) { heightPx.toDp() + chipExtraOffset }
                    }
            ) {
                SearchInputField(
                    query = uiState.query,
                    onTextChanged = viewModel::onQueryChanged,
                    onClearClick = { viewModel.onQueryChanged("") }
                )
            }

            // 1. Плейсхолдер, когда пользователь ещё ничего не искал
            if (uiState.isInitial) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_search_placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(SEARCH_PLACEHOLDER_WIDTH_FRACTION)
                            .aspectRatio(SEARCH_PLACEHOLDER_ASPECT_RATIO),
                        contentScale = ContentScale.Fit
                    )
                }
            } else {
                // 2. Ошибка
                if (uiState.errorType != SearchErrorType.NONE) {
                    Text(
                        text = when (uiState.errorType) {
                            SearchErrorType.NETWORK ->
                                stringResource(R.string.error_network)
                            SearchErrorType.GENERAL ->
                                stringResource(R.string.error_general)
                            else -> ""
                        },
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // 3. Содержимое: лоадер / пусто / список
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

//                    uiState.vacancies.isEmpty() &&
//                        uiState.errorType == SearchErrorType.NONE -> {
//                        Text(
//                            text = stringResource(R.string.vacancy_search_empty),
//                            modifier = Modifier.padding(16.dp),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = chipHeightState.value + 8.dp
                            )
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
            }
        }

        // 🔹 Чип поверх списка, приклеен под полем поиска
        if (!uiState.isInitial && (uiState.totalFound > 0 || noResults)) {
            val baseModifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = chipTopOffsetState.value)
                .onGloballyPositioned { coordinates ->
                    val hPx = coordinates.size.height.toFloat()
                    chipHeightState.value = with(density) { hPx.toDp() }
                }

            if (uiState.totalFound > 0) {
                // 🔵 нашли вакансии
                SearchCountChip(
                    total = uiState.totalFound,
                    modifier = baseModifier
                )
            } else {
                // 🔵 результатов нет, noResults здесь и так всегда true
                Box(
                    modifier = baseModifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                    ) {
                        Text(
                            text = stringResource(R.string.vacancy_search_empty),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            }
        }
    }
}
