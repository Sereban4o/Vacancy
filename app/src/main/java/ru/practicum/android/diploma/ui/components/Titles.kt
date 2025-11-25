package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import ru.practicum.android.diploma.ui.theme.PaddingScreenTitleVertical
import ru.practicum.android.diploma.ui.theme.PaddingSmall

/**
 * Базовый компонент для всех заголовков.
 *
 * Используется для Heading(), DisplayTitle() и других заголовков.
 */
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

/**
 * Основной заголовок экрана.
 *
 * Это то, что Виталий делал — titleLarge + вертикальный padding.
 * Совпадает поэтому ничего не изменил
 */
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

/**
 * Более крупный заголовок (используется реже).
 * идет от Сергея
 */
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
