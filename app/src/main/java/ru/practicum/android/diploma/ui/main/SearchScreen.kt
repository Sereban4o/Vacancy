package ru.practicum.android.diploma.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.SearchCountChip
import ru.practicum.android.diploma.ui.components.SearchInputField
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.ui.theme.PaginationLoaderHeight
import ru.practicum.android.diploma.ui.theme.PaginationLoaderSize
import ru.practicum.android.diploma.ui.theme.ResultsChipBlue
import ru.practicum.android.diploma.util.TypeState

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value

    // 🔹 Paging-данные
    val pagedData: LazyPagingItems<Vacancy> =
        viewModel.pagingResultDataFlow.collectAsLazyPagingItems()

    // 🔥 Синхронизируем loadState Paging'а с uiState во ViewModel
    LaunchedEffect(pagedData.loadState) {
        viewModel.onLoadStateChanged(pagedData.loadState)
    }

    // 🔹 Логика чипа
    val density = LocalDensity.current
    val chipExtraOffset = 5.dp
    val chipTopOffsetState = remember { mutableStateOf(0.dp) }
    val chipHeightState = remember { mutableStateOf(0.dp) }

    // noResults — через Paging
    val noResults = !uiState.isInitial &&
        !uiState.isLoading &&
        uiState.errorType == SearchErrorType.NONE &&
        pagedData.itemCount == 0 &&
        pagedData.loadState.refresh is LoadState.NotLoading

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

                // 4️⃣ Загрузка первой страницы — крутим по центру
                uiState.isLoading && pagedData.itemCount == 0 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // 5️⃣ Вакансий нет
                noResults -> {
                    InfoState(TypeState.NoDataVacancy)
                }

                // 6️⃣ Список вакансий (Paging 3)
                else -> {
                    PagedVacanciesList(
                        pagedData = pagedData,
                        topPadding = chipHeightState.value + 8.dp,
                        onVacancyClick = onVacancyClick
                    )
                }
            }
        }

        // 🔹 Чип поверх списка — как в старой реализации
        if (!uiState.isInitial && (uiState.totalFound > 0 || noResults)) {
            val baseModifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = chipTopOffsetState.value)
                .onGloballyPositioned { coordinates ->
                    val hPx = coordinates.size.height.toFloat()
                    chipHeightState.value = with(density) { hPx.toDp() }
                }

            if (uiState.totalFound > 0) {
                SearchCountChip(
                    total = uiState.totalFound,
                    modifier = baseModifier
                )
            } else {
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

        // 🔥 Индикатор пагинации поверх, с "пустым" фоном под ним
        val isAppending = pagedData.loadState.append is LoadState.Loading
        if (isAppending && pagedData.itemCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 0.dp)
            ) {
                // полоса фона, чтобы под кружком было пусто
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(PaginationLoaderHeight)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(PaginationLoaderSize),
                        color = ResultsChipBlue,
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

/**
 * Список вакансий c Paging 3, с отступом под чип.
 * Лоадер пагинации рисуется оверлеем в SearchScreen.
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
            bottom = 16.dp
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
    }
}
