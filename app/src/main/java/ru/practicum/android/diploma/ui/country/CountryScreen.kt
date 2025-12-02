package ru.practicum.android.diploma.ui.country

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.country.CountryUiState
import ru.practicum.android.diploma.presentation.country.CountryViewModel
import ru.practicum.android.diploma.ui.components.BackButton
import ru.practicum.android.diploma.ui.components.Heading
import ru.practicum.android.diploma.ui.components.InfoState
import ru.practicum.android.diploma.ui.components.ScreenScaffold
import ru.practicum.android.diploma.ui.components.FullscreenProgress
import ru.practicum.android.diploma.util.TypeState

@Composable
fun CountryScreen(
    onBack: () -> Unit,
    onCountryChosen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CountryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    ScreenScaffold(
        modifier = modifier,
        topBar = {
            Heading(
                text = stringResource(R.string.country_title), // "Выбор страны"
                leftBlock = { BackButton(onBack) }
            )
        },
        content = {
            when {
                uiState.isLoading -> {
                    FullscreenProgress()
                }

                uiState.isError -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.isNetworkError) {
                            InfoState(TypeState.NoInternet)
                        } else {
                            InfoState(TypeState.ServerError)
                        }
                    }
                }

                else -> {
                    CountryList(
                        uiState = uiState,
                        onCountryClick = { id ->
                            coroutineScope.launch {
                                val ok = viewModel.selectCountry(id)
                                if (ok) onBack()
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun CountryList(
    uiState: CountryUiState,
    onCountryClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = uiState.countries,
            key = { it.id }
        ) { country ->
            CountryRow(
                name = country.name,
                onClick = { onCountryClick(country.id) }
            )
        }
    }
}

@Composable
private fun CountryRow(
    name: String,
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
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Icon(
            painter = painterResource(R.drawable.arrow_forward_24px),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
