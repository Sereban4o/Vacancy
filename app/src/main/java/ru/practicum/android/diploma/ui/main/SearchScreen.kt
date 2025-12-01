package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.components.CenteredProgress
import ru.practicum.android.diploma.ui.components.FullscreenProgress
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.ScreenScaffold
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

    // Paging-данные
    val pagedData: LazyPagingItems<Vacancy> =
        viewModel.pagingResultDataFlow.collectAsLazyPagingItems()

    // Синхронизуем loadState Paging'а с uiState во ViewModel (ошибки/загрузка)
    LaunchedEffect(pagedData.loadState) {
        viewModel.onLoadStateChanged(pagedData.loadState)
    }

    // Логика чипа
    val density = LocalDensity.current
    val chipExtraOffset = 5.dp
    val chipTopOffsetState = remember { mutableStateOf(0.dp) }
    val chipHeightState = remember { mutableStateOf(0.dp) }

    // флаг «вакансий нет»
    val noResults = !uiState.isInitial &&
        !uiState.isLoading &&
        uiState.errorType == SearchErrorType.NONE &&
        pagedData.itemCount == 0 &&
        pagedData.loadState.refresh is LoadState.NotLoading

    ScreenScaffold(
        modifier = modifier,
        topBar = {
            Box( // 🔹 Поле поиска
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
        },
        content = {
            when { // 🔥 БЛОК СОСТОЯНИЙ ЭКРАНА
                uiState.isInitial -> { // 1️⃣ Первый запуск
                    InfoState(TypeState.SearchVacancy)
                }

                uiState.errorType == SearchErrorType.NETWORK -> {
                    InfoState(TypeState.NoInternet)
                } // 2️⃣ Ошибка — нет интернета

                uiState.errorType == SearchErrorType.GENERAL -> {
                    InfoState(TypeState.ServerError)
                } // 3️⃣ Ошибка — сервер

                uiState.isLoading && uiState.query.isNotEmpty() -> {
                    FullscreenProgress()
                } // 4️⃣ Загрузка первой страницы — пока список пустой

                noResults -> { // 5️⃣ Вакансий нет
                    InfoState(TypeState.NoDataVacancy)
                }

                else -> { // 6️⃣ Список вакансий (Paging 3)
                    PagedVacanciesList(
                        pagedData = pagedData,
                        topPadding = chipHeightState.value + 8.dp,
                        onVacancyClick = onVacancyClick
                    )
                }
            }
        },
        overlay = { // 🔹 Чип поверх списка
            if (!uiState.isInitial && (uiState.totalFound > 0 || noResults)) {
                val baseModifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = chipTopOffsetState.value)
                    .onGloballyPositioned { coordinates ->
                        val hPx = coordinates.size.height.toFloat()
                        chipHeightState.value = with(density) { hPx.toDp() }
                    }
                ShowChip(uiState.totalFound, baseModifier)
            }
        }
    )
}

@Composable
private fun ShowChip(
    totalFound: Int,
    modifier: Modifier
) {
    if (totalFound > 0) { // ✔ нашли вакансии
        SearchCountChip(
            total = totalFound,
            modifier = modifier
        )
    } else { // ✔ вакансий нет — чип с текстом
        Surface(
            modifier = modifier,
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

/**
 * Список вакансий с Paging 3 + индикатор дозагрузки внизу.
 */
@Composable
private fun PagedVacanciesList(
    pagedData: LazyPagingItems<Vacancy>,
    topPadding: Dp,
    onVacancyClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = topPadding,
            bottom = 16.dp // обычный "воздух" снизу, без костылей под навбар
        )
    ) {
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

        // 🔹 нижний индикатор при подгрузке следующей страницы
        // (как советовал наставник и сделал Андрей)
        if (pagedData.loadState.append is LoadState.Loading) {
            item {
                CenteredProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
