package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.CornerRadiusLarge
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundDark
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundLight
import ru.practicum.android.diploma.ui.theme.SearchFieldTextColor

@Composable
fun SearchInputField(
    query: String,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    // фон поля по ТЗ
    val fieldBackground = if (isDark) {
        SearchFieldBackgroundDark // #AEAFB4
    } else {
        SearchFieldBackgroundLight // #E6E8EB
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = query,
        onValueChange = onTextChanged,

        placeholder = {
            Text(
                text = stringResource(R.string.vacancy_text_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },

        trailingIcon = {
            if (query.isNotEmpty()) {
                ActionIcon(
                    iconRes = R.drawable.ic_clear_24,
                    onClick = onClearClick
                )
            } else {
                ActionIcon(iconRes = R.drawable.ic_search_24)
            }
        },

        colors = TextFieldDefaults.colors(
            focusedTextColor = SearchFieldTextColor, // #1A1B22
            unfocusedTextColor = SearchFieldTextColor, // #1A1B22

            // 🔵 КУРСОР — твой #3772E7 из темы
            cursorColor = MaterialTheme.colorScheme.tertiary,

            // 🔘 ФОН ПОЛЯ — #E6E8EB
            focusedContainerColor = fieldBackground, // день/ночь разные фоны
            unfocusedContainerColor = fieldBackground,

            // плейсхолдер
            focusedPlaceholderColor = SearchFieldTextColor.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = SearchFieldTextColor.copy(alpha = 0.6f),

            // скрываем линию
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),

        singleLine = true,
        shape = RoundedCornerShape(CornerRadiusLarge),
        textStyle = MaterialTheme.typography.bodyLarge
    )
}
