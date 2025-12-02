package ru.practicum.android.diploma.ui.workplace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.workplace.WorkPlaceViewModel
import ru.practicum.android.diploma.ui.components.BackButton
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.PrimaryBottomButton
import ru.practicum.android.diploma.ui.components.ScreenScaffold

@Composable
fun WorkPlaceScreen(
    onBack: () -> Unit,
    onCountryClick: () -> Unit, // заглушки на будущие экраны
    onRegionClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkPlaceViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // 🔹 при каждом заходе на экран загружаем актуальные страна/регион
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    ScreenScaffold(
        modifier = modifier,
        topBar = {
            Heading(
                text = stringResource(R.string.work_place_title), // "Выбор места работы"
                leftBlock = { BackButton(onBack) }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(Modifier.height(16.dp))

                WorkPlaceRow(
                    titleRes = R.string.country,
                    value = uiState.country?.name,
                    onRowClick = onCountryClick,
                    onClearClick = { viewModel.onClearCountry() }
                )

                Spacer(Modifier.height(16.dp))

                WorkPlaceRow(
                    titleRes = R.string.region,
                    value = uiState.region?.name,
                    onRowClick = onRegionClick,
                    onClearClick = { viewModel.onClearRegion() }
                )
            }
        },
        overlay = {
            if (uiState.hasSelection) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    PrimaryBottomButton(
                        textRes = R.string.choose,
                        onClick = {
                            coroutineScope.launch {
                                val ok = viewModel.applySelection()
                                if (ok) onBack()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}
