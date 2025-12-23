package ru.practicum.android.diploma.ui.filter.region

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.region.RegionUiState
import ru.practicum.android.diploma.presentation.filter.region.RegionViewModel
import ru.practicum.android.diploma.ui.components.BackButton
import ru.practicum.android.diploma.ui.components.FullscreenProgress
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.ScreenScaffold
import ru.practicum.android.diploma.ui.components.SearchInputField
import ru.practicum.android.diploma.ui.theme.SearchFieldTextColor
import ru.practicum.android.diploma.util.TypeState

@Composable
fun RegionScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // Явный запуск загрузки регионов (без init в ViewModel)
    LaunchedEffect(Unit) {
        viewModel.loadRegions()
    }

    ScreenScaffold(
        modifier = modifier,
        topBar = {
            Heading(
                text = stringResource(R.string.region_title), // "Выбор региона"
                leftBlock = { BackButton(onBack) }
            )
        },
        content = {
            when {
                uiState.isLoading -> {
                    FullscreenProgress()
                }
                else -> {
                    RegionContent(
                        uiState = uiState,
                        onQueryChanged = viewModel::onQueryChanged,
                        onRegionClick = { id ->
                            coroutineScope.launch {
                                val ok = viewModel.selectRegion(id)
                                if (ok) onBack() // возвращаемся на WorkPlaceScreen
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun RegionContent(
    uiState: RegionUiState,
    onQueryChanged: (String) -> Unit,
    onRegionClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 🔍 поле ввода "Введите регион"
        SearchInputField(
            query = uiState.query,
            onTextChanged = onQueryChanged,
            onClearClick = { onQueryChanged("") },
            placeholderText = stringResource(R.string.region_search_hint)
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isEmptySearchResult -> {
                    // "Такого региона нет"
                    InfoState(TypeState.NoRegion)
                }

                uiState.isError -> {
                    // "Не удалось получить список"
                    InfoState(TypeState.NoDataRegion)
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = uiState.regions,
                            key = { it.id }
                        ) { region ->
                            RegionRow(
                                name = region.name,
                                onClick = { onRegionClick(region.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RegionRow(
    name: String,
    onClick: () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        androidx.compose.material3.Icon(
            painter = painterResource(R.drawable.arrow_forward_24px),
            contentDescription = null,
            tint = SearchFieldTextColor // как на WorkPlace/Страна
        )
    }
}
