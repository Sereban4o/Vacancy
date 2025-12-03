package ru.practicum.android.diploma.ui.filter

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
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.ui.components.BackButton
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.PrimaryBottomButton
import ru.practicum.android.diploma.ui.components.SalaryOnlyCheckbox
import ru.practicum.android.diploma.ui.components.ScreenScaffold
import ru.practicum.android.diploma.ui.components.SecondaryTextButton
import ru.practicum.android.diploma.ui.filter.workplace.WorkPlaceRow

@Composable
fun FilterSettingsScreen(
    onBack: () -> Unit,
    onWorkPlaceClick: () -> Unit,
    onIndustryClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    // перечитываем сохранённые FilterSettings
    // когда экран попадает в composition (в т.ч. после возврата с WorkPlace / Industry)
    LaunchedEffect(Unit) {
        viewModel.refreshFromRepository()
    }

    ScreenScaffold(
        modifier = modifier,
        topBar = {
            Heading(
                text = stringResource(R.string.filter_title), // "Настройки фильтрации"
                leftBlock = { BackButton(onBack) }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(Modifier.height(16.dp))

                // ---- Место работы ----
                WorkPlaceRow(
                    titleRes = R.string.work_place_title, // "Место работы"
                    value = uiState.workPlaceLabel,
                    onRowClick = onWorkPlaceClick,
                    onClearClick = { viewModel.clearWorkPlace() }
                )

                Spacer(Modifier.height(16.dp))

                // ---- Отрасль ----
                WorkPlaceRow(
                    titleRes = R.string.industry, // "Отрасль"
                    value = uiState.industry?.name,
                    onRowClick = onIndustryClick,
                    onClearClick = { viewModel.setIndustry(null) }
                )

                Spacer(Modifier.height(24.dp))

                // ---- Ожидаемая зарплата (двухстрочное поле из макета) ----
                ExpectedSalaryField(
                    value = uiState.salaryText,
                    onValueChange = viewModel::onSalaryChanged,
                    onClear = viewModel::clearSalary,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // ---- Чекбокс "Не показывать без зарплаты" ----
                SalaryOnlyCheckbox(
                    checked = uiState.withSalaryOnly,
                    onCheckedChange = viewModel::toggleWithSalaryOnly,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        overlay = {
            if (uiState.hasAnyFilter) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    PrimaryBottomButton(
                        textRes = R.string.apply, // "Применить"
                        onClick = {
                            viewModel.apply()
                            onBack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    SecondaryTextButton(
                        textRes = R.string.reset, // "Сбросить"
                        onClick = { viewModel.resetAll() }
                    )
                }
            }
        }
    )
}
