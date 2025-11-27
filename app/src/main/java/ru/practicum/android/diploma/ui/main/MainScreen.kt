package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Heading(
                text = stringResource(R.string.mainHeading),
                modifier = Modifier.weight(1f)   // ← заголовок занимает всё слева
            )

            ActionIcon(
                iconRes = R.drawable.ic_filter_24,
                onClick = onFilterClick
            )
        }

        // 🔍 Реальный экран поиска
        SearchScreen(
            viewModel = searchViewModel,
            onVacancyClick = onVacancyClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}
