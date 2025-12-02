package ru.practicum.android.diploma.ui.industry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.practicum.android.diploma.presentation.industry.IndustryUiState
import ru.practicum.android.diploma.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.ui.components.BackButton
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.PrimaryBottomButton
import ru.practicum.android.diploma.ui.components.ScreenScaffold
import ru.practicum.android.diploma.ui.components.SearchInputField
import ru.practicum.android.diploma.ui.theme.BoxBackground
import ru.practicum.android.diploma.util.TypeState

@Composable
fun IndustryScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IndustryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    ScreenScaffold(
        modifier = modifier,
        topBar = {
            Heading(
                text = stringResource(R.string.industry_title),
                leftBlock = { BackButton(onBack) }
            )
        },
        content = {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.isError -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        InfoState(TypeState.ServerError)
                    }
                }

                else -> {
                    IndustryContent(
                        uiState = uiState,
                        onQueryChanged = viewModel::onQueryChanged,
                        onIndustryClick = { id -> viewModel.onIndustryClick(id) }
                    )
                }
            }
        },
        overlay = {
            // Кнопка "Выбрать" внизу, только если есть выбранный элемент
            if (uiState.selectedIndustryId != null) {
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
                                val applied = viewModel.applySelection()
                                if (applied) onBack()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}

@Composable
private fun IndustryContent(
    uiState: IndustryUiState,
    onQueryChanged: (String) -> Unit,
    onIndustryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🔍 поле поиска отрасли
        SearchInputField(
            query = uiState.query,
            onTextChanged = onQueryChanged,
            onClearClick = { onQueryChanged("") },
            placeholderText = stringResource(R.string.industry_search_hint) // 👈 "Введите отрасль"
        )

        Spacer(Modifier.height(12.dp))

        if (uiState.industries.isEmpty() && uiState.query.isNotBlank()) {
            // ❌ Ничего не нашли по введённому тексту
            InfoState(TypeState.NoIndustry)
        } else {
            // 📋 список отраслей (полный или отфильтрованный)
            LazyColumn {
                items(
                    items = uiState.industries,
                    key = { it.id }
                ) { industry ->
                    IndustryListItem(
                        name = industry.name,
                        isSelected = industry.id == uiState.selectedIndustryId,
                        onClick = { onIndustryClick(industry.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun IndustryListItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 👈 Текст слева
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium, // Regular/16
            color = MaterialTheme.colorScheme.onBackground // чёрный/белый день/ночь
        )

        // 👉 radio-иконка справа
        val iconRes = if (isSelected) {
            R.drawable.radio_button_checked_24px
        } else {
            R.drawable.radio_button_off__24px
        }

        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = BoxBackground // #3772E7 — как на макете
        )
    }
}
