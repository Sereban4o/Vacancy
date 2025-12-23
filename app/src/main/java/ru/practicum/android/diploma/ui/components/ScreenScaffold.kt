package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            topBar?.invoke()
            content()
        }

        // Для всяких плавающих элементов (как чип в поиске)
        overlay()
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenScaffoldPreview() {
    MaterialTheme {
        ScreenScaffold(
            topBar = { Text("TopBar") },
            content = { Text("Content") }
        )
    }
}
