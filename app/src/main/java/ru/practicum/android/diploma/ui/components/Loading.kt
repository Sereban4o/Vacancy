package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Центрированный progress bar.
 * Поведение задаём через modifier (full screen, по ширине и т.п.).
 */
@Composable
fun CenteredProgress(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Удобный шорткат для полноэкранной загрузки.
 */
@Composable
fun FullscreenProgress() {
    CenteredProgress(Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
private fun CenteredProgressPreview() {
    MaterialTheme {
        CenteredProgress(
            modifier = Modifier.fillMaxSize()
        )
    }
}
