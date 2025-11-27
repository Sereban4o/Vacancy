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
 * Через него проходят:
 *  - Heading()           — основной заголовок экрана (Medium/22)
 *  - DisplayTitle()      — крупный заголовок (Bold/32)
 *
 * Здесь настраиваются:
 *  - цвет текста        — onBackground
 *  - ширина             — fillMaxWidth()
 *  - вертикальные отступы
 *  - общий стиль передаётся через параметр style
 *
 * Этот компонент — универсальный строитель заголовков,
 * чтобы не дублировать Text() с одинаковой разметкой.
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
 * Соответствует макету:
 *  - Medium / 22px / 26px / 0%
 *  - YS Display (500)
 *
 * Применяется на всех обычных экранах:
 *   — "Поиск"
 *   — "Избранное"
 *   — "Команда"  → слово "Команда" наверху
 *   — "Настройки"
 *
 * Это стандартный "title" уровня страницы.
 */
@Composable
fun Heading(
    modifier: Modifier = Modifier,
    text: String
) {
    BaseTitleText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium, // Medium/22
        verticalPadding = PaddingScreenTitleVertical,
    )
}

/**
 * Крупный заголовок.
 *
 * Соответствует макету:
 *  - Bold / 32px / 38px / 0%
 *  - YS Display (700)
 *
 * Используется в двух местах:
 *   1) Экран "Команда":
 *        DisplayTitle("Наша команда")
 *        — это большой подзаголовок под основным.
 *
 *   2) Экран "Детали вакансии":
 *        DisplayTitle(vacancy.title)
 *        — это главный заголовок вакансии.
 *
 * Это "hero title" — для крупных, визуально важных заголовков.
 */
@Composable
fun DisplayTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    BaseTitleText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.displayLarge, // Bold/32
        verticalPadding = PaddingSmall,
    )
}
