package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.PaddingSmall
import ru.practicum.android.diploma.ui.theme.TextSizeLarge

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
    verticalPadding: Dp = 0.dp,
) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
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
    text: String,
    leftBlock: @Composable (() -> Unit)? = null,
    rightBlock: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftBlock?.invoke()
        BaseTitleText(
            text = text,
            verticalPadding = 10.dp,
            style = TextStyle(
                color = colorResource(R.color.text_color),
                fontFamily = FontFamily(Font(R.font.ys_display_medium)),
                fontSize = TextSizeLarge,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(Modifier.weight(1f))
        rightBlock?.invoke()
    }
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
