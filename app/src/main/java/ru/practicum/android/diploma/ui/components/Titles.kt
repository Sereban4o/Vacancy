package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.theme.PaddingScreenTitleVertical
import ru.practicum.android.diploma.ui.theme.PaddingSmall

/**
 * Базовый компонент для всех заголовков.
 *
 * Через него проходят:
 *  - Heading()      — основной заголовок экрана
 *  - DisplayTitle() — крупный заголовок (hero title)
 *
 * Здесь настраиваются:
 *  - цвет текста          — colorScheme.onBackground
 *  - базовые отступы по вертикали
 *  - остальное (размер, жирность, шрифт) задаётся через style
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
        // единый источник цвета — тема
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .padding(vertical = verticalPadding)
    )
}

/**
 * Основной заголовок экрана.
 *
 * Соответствует макету:
 *  - Medium / 22px (YS Display 500) — настроено в MaterialTheme.typography.titleMedium
 *
 * Особенности:
 *  - leftBlock — слот слева (стрелка "Назад", иконка, аватар и т.п.)
 *  - rightBlock — слот справа (share, избранное, меню и т.п.)
 *
 * Применяется на экранах:
 *   — "Поиск"
 *   — "Избранное"
 *   — "Команда"
 *   — "Настройки"
 *   — "Фильтры"
 *   — "Детали вакансии" (в роли заголовка "Вакансия")
 */
@Composable
fun Heading(
    text: String,
    modifier: Modifier = Modifier,
    leftBlock: (@Composable () -> Unit)? = null,
    rightBlock: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка/контент слева (может быть null)
        leftBlock?.invoke()

        // Сам заголовок занимает "центральное" место
        BaseTitleText(
            text = text,
            modifier = Modifier
                .weight(1f), // тянем текст между левым и правым блоками
            style = MaterialTheme.typography.titleMedium, // Medium/22 (YS Display 500 в теме)
            verticalPadding = PaddingScreenTitleVertical,
        )

        // Иконки/контент справа (может быть null)
        rightBlock?.invoke()
    }
}

/**
 * Крупный заголовок.
 *
 * Соответствует макету:
 *  - Bold / 32px (YS Display 700) — настроено в MaterialTheme.typography.displayLarge
 *
 * Используется:
 *   1) Экран "Команда" — большой подзаголовок под основным.
 *   2) Экран "Детали вакансии" — заголовок самой вакансии.
 */
@Composable
fun DisplayTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    BaseTitleText(
        text = text,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.displayLarge, // Bold/32
        verticalPadding = PaddingSmall,
    )
}
