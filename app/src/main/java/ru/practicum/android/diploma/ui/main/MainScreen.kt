package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.components.ActionIcon
import ru.practicum.android.diploma.ui.components.Heading

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit,
    onVacancyClick: (String) -> Unit
) {
    val searchViewModel: SearchViewModel = koinViewModel()

    Column(modifier = modifier.fillMaxSize()) {
        Heading(
            text = stringResource(R.string.mainHeading),
            // modifier можно пробросить, если сверху есть padding у Column
            rightBlock = {
                ActionIcon(
                    iconRes = R.drawable.ic_filter_18_12,
                    onClick = onFilterClick
                )
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
