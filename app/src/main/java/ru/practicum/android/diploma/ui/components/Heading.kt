package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.theme.PaddingScreenTitleVertical

@Composable
fun Heading(
    modifier: Modifier,
    text: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = PaddingScreenTitleVertical)
        )
    }
}
