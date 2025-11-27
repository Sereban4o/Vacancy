package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.SearchCountChip
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

    val noResults = !uiState.isInitial &&
        !uiState.isLoading &&
        uiState.errorType == SearchErrorType.NONE &&
        uiState.vacancies.isEmpty()

    val density = LocalDensity.current
    val chipExtraOffset = 5.dp
    val chipTopOffsetState = remember { mutableStateOf(0.dp) }
    val chipHeightState = remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 🔹 Поле поиска
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

            // 🔥 БЛОК СОСТОЯНИЙ ЭКРАНА
            when {

                // 1️⃣ Первый запуск
                uiState.isInitial -> {
                    InfoState(TypeState.SearchVacancy)
                }

                // 2️⃣ Ошибка — нет интернета
                uiState.errorType == SearchErrorType.NETWORK -> {
                    InfoState(TypeState.NoInternet)
                }

                // 3️⃣ Ошибка — сервер
                uiState.errorType == SearchErrorType.GENERAL -> {
                    InfoState(TypeState.ServerError)
                }

                // 4️⃣ Загрузка
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // 5️⃣ Вакансий нет (важно!)
                noResults -> {
                    // В центре — картинка + текст
                    InfoState(TypeState.NoDataVacancy)
                }

                // 6️⃣ Список вакансий
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = chipHeightState.value + 8.dp
                        )
                    ) {
                        items(uiState.vacancies, key = { it.id }) { vacancy ->
                            VacancyItem(
                                vacancy = vacancy,
                                onClick = { onVacancyClick(vacancy.id) }
                            )
                        }
                    }
                }
            }
        }

        // 🔹 Чип поверх списка — работает как раньше
        if (!uiState.isInitial && (uiState.totalFound > 0 || noResults)) {

            val baseModifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = chipTopOffsetState.value)
                .onGloballyPositioned { coordinates ->
                    val hPx = coordinates.size.height.toFloat()
                    chipHeightState.value = with(density) { hPx.toDp() }
                }

            if (uiState.totalFound > 0) {
                // ✔ нашли вакансии
                SearchCountChip(
                    total = uiState.totalFound,
                    modifier = baseModifier
                )
            } else {
                // ✔ вакансий нет — чип с текстом
                Surface(
                    modifier = baseModifier,
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
