package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.PaddingExtraLarge
import ru.practicum.android.diploma.ui.theme.PaddingMedium
import ru.practicum.android.diploma.util.TypeState

@Composable
fun InfoState(state: TypeState) {
    when (state) {
        TypeState.SearchVacancy -> {
            Info(painterResource(R.drawable.search_vacancy))
        }

        TypeState.NoInternet -> {
            Info(
                painterResource(
                    R.drawable.no_internet
                ),
                stringResource(R.string.no_internet)
            )
        }

        TypeState.NoDataVacancy -> {
            Info(
                painterResource(
                    R.drawable.no_data_vacancy
                ),
                stringResource(R.string.no_data_vacancy)
            )
        }

        TypeState.NoRegion -> {
            Info(
                painterResource(
                    R.drawable.no_data_vacancy
                ),
                stringResource(R.string.no_region)
            )
        }

        TypeState.NoDataRegion -> {
            Info(
                painterResource(
                    R.drawable.no_data_region
                ),
                stringResource(R.string.no_data_region)
            )
        }

        TypeState.NoVacancy -> {
            Info(
                painterResource(
                    R.drawable.no_vacancy
                ),
                stringResource(R.string.no_vacancy)
            )
        }

        TypeState.ServerError -> {
            Info(
                painterResource(
                    R.drawable.server_error
                ),
                stringResource(R.string.server_error)
            )
        }

        TypeState.ServerErrorVacancy -> {
            Info(
                painterResource(
                    R.drawable.server_error_vacancy
                ),
                stringResource(R.string.server_error)
            )
        }

        TypeState.EmptyList -> {
            Info(
                painterResource(
                    R.drawable.empty_list
                ),
                stringResource(R.string.empty_list)
            )
        }

        TypeState.NoIndustry -> {
            Info(
                painterResource(
                    R.drawable.empty_list
                ),
                stringResource(R.string.industry_error)
            )
        }
    }
}

@Composable
private fun Info(painter: Painter, text: String = "") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = PaddingMedium),
            contentDescription = null,
            painter = painter
        )
        if (text.isNotEmpty()) {
            Text(
                text = text,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = PaddingExtraLarge)
                    .padding(top = PaddingMedium),
                // макет: Medium / 22, weight 500, 22px, 26px, 0%, centered 💯
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }

}
