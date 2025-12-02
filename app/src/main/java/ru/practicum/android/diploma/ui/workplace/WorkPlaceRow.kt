package ru.practicum.android.diploma.ui.workplace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundDark

@Composable
fun WorkPlaceRow(
    titleRes: Int, // R.string.country / R.string.region
    value: String?, // null -> не выбран
    onRowClick: () -> Unit, // переход на экран выбора
    onClearClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onRowClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Подпись "Страна"/"Регион"
            val labelColor = if (value == null) {
                SearchFieldBackgroundDark // серый, как в макете
            } else {
                MaterialTheme.colorScheme.onBackground
            }

            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.bodyMedium,
                color = labelColor
            )

            // Значение (Россия / Москва)
            if (value != null) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Иконка справа:
        //  - если НЕ выбрано → стрелка
        //  - если выбрано → крестик (очистить)
        val iconRes = if (value == null) {
            R.drawable.arrow_forward_24px
        } else {
            R.drawable.ic_clear_24
        }

        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}
