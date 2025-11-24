package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import ru.practicum.android.diploma.ui.theme.PaddingScreenHorizontal
import ru.practicum.android.diploma.ui.theme.PaddingScreenTitleVertical
import ru.practicum.android.diploma.ui.theme.PaddingSmall

@Composable
private fun BaseTitleText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    verticalPadding: Dp,
) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
    )
}

@Composable
fun Heading(
    modifier: Modifier = Modifier,
    text: String
) {
    BaseTitleText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        verticalPadding = PaddingScreenTitleVertical,
    )
}

@Composable
fun DisplayTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    BaseTitleText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.displayLarge,
        verticalPadding = PaddingSmall,
    )
}
