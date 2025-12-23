package ru.practicum.android.diploma.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.FavoriteActive

@Composable
fun SecondaryTextButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.bodyMedium,
            color = FavoriteActive // красный, как в макете
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryTextButtonPreview() {
    MaterialTheme {
        SecondaryTextButton(
            textRes = R.string.reset,
            onClick = {}
        )
    }
}
