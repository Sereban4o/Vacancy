package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.components.ActionIcon
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.theme.BoxBackground
import ru.practicum.android.diploma.ui.theme.TextColorDark

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    onFilterClick: () -> Unit,
    onVacancyClick: (String) -> Unit
) {
    val uiState = searchViewModel.uiState.collectAsState().value

    // 🔹 КАЖДЫЙ раз, когда MainScreen попадает в композицию (в т.ч. после popBackStack с фильтра),
    // подтягиваем актуальное состояние фильтра из хранилища
    LaunchedEffect(Unit) {
        searchViewModel.refreshFilterState()
    }

    Column(modifier = modifier.fillMaxSize()) {
        Heading(
            text = stringResource(R.string.mainHeading),
            // modifier можно пробросить, если сверху есть padding у Column
            rightBlock = {
                if (uiState.hasActiveFilter) {
                    // 🔵 АКТИВНЫЙ фильтр — синий фон + белая иконка
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = BoxBackground, // #3772E7
                                shape = RoundedCornerShape(4.dp) // радиус 4dp
                            )
                    ) {
                        ActionIcon(
                            modifier = Modifier.fillMaxSize(),
                            iconRes = R.drawable.ic_filter_18_12,
                            onClick = onFilterClick,
                            tint = TextColorDark // #FDFDFD
                        )
                    }
                } else {
                    // ⚪ НЕактивный фильтр — без фона, обычный цвет
                    ActionIcon(
                        iconRes = R.drawable.ic_filter_18_12,
                        onClick = onFilterClick,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        )

        // 🔍 Реальный экран поиска
        SearchScreen(
            viewModel = searchViewModel,
            onVacancyClick = onVacancyClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}
