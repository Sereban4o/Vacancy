package ru.practicum.android.diploma.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.theme.BoxBackground
import ru.practicum.android.diploma.ui.theme.TextColorDark

/**
 * Универсальная нижняя синяя кнопка по макету:
 * высота 59dp, радиус 12dp, фон #3772E7, текст белый.
 *
 * Можно переиспользовать на экранах фильтров, индустрий, регионов и т.д.
 */
@Composable
fun PrimaryBottomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(59.dp), // 🔹 высота по макету
        shape = RoundedCornerShape(12.dp), // 🔹 радиус 12dp
        colors = ButtonDefaults.buttonColors(
            containerColor = BoxBackground, // 🔹 синий фон
            contentColor = TextColorDark // 🔹 белый текст
        )
    ) {
        Text(text = text)
    }
}

/**
 * Перегрузка с @StringRes, чтобы удобно было передавать id строки.
 */
@Composable
fun PrimaryBottomButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryBottomButton(
        text = stringResource(id = textRes),
        onClick = onClick,
        modifier = modifier
    )
}
