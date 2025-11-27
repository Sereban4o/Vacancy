package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.SpacerMedium

@Composable
fun ContentPlaceholder(
    modifier: Modifier = Modifier,
    imageRes: Int,
    headlineText: String = ""
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = headlineText
        )
        Spacer(modifier = Modifier.height(SpacerMedium))
        Text(
            text = headlineText,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPlaceholderPreviewLight() {
    AppTheme {
        ContentPlaceholder(
            imageRes = R.drawable.ic_no_found,
            headlineText = stringResource(R.string.no_vacancies_error_headline)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPlaceholderPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ContentPlaceholder(
            imageRes = R.drawable.ic_no_content,
            headlineText = "TEXT"
        )
    }
}
