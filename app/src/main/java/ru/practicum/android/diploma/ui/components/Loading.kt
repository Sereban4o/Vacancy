package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
