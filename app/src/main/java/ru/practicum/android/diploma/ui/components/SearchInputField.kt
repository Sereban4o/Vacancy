package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.CornerRadiusLarge
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundDark
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundLight
import ru.practicum.android.diploma.ui.theme.SearchFieldTextColor
import ru.practicum.android.diploma.ui.theme.TextColorDark

@Composable
fun SearchInputField(
    query: String,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    placeholderText: String = stringResource(R.string.vacancy_text_placeholder),
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val isDark = isSystemInDarkTheme()

    // фон по ТЗ
    val fieldBackground = if (isDark) {
        SearchFieldBackgroundDark // ночь: #AEAFB4
    } else {
        SearchFieldBackgroundLight // день: #E6E8EB
    }

    // цвет плейсхолдера по ТЗ
    val placeholderColor = if (isDark) {
        TextColorDark // ночь: #FDFDFD
    } else {
        SearchFieldBackgroundDark // день: #AEAFB4
    }

    // 🎯 Цвет иконок (поиск / очистка) — ВСЕГДА #1A1B22
    val iconTint = SearchFieldTextColor

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = query,
        onValueChange = onTextChanged,

        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyLarge,
                color = placeholderColor
            )
        },

        trailingIcon = {
            if (query.isNotEmpty()) {
                ActionIcon(
                    iconRes = R.drawable.ic_clear_24,
                    onClick = onClearClick,
                    tint = iconTint // #1A1B22
                )
            } else {
                ActionIcon(
                    iconRes = R.drawable.ic_search_24,
                    tint = iconTint // #1A1B22
                )
            }
        },

        colors = TextFieldDefaults.colors(
            focusedTextColor = SearchFieldTextColor, // #1A1B22
            unfocusedTextColor = SearchFieldTextColor,

            // 🔵 КУРСОР — твой #3772E7 из темы
            cursorColor = MaterialTheme.colorScheme.tertiary,

            // 🔘 ФОН ПОЛЯ — #E6E8EB
            focusedContainerColor = fieldBackground, // день/ночь разные фоны
            unfocusedContainerColor = fieldBackground,

            focusedPlaceholderColor = placeholderColor,
            unfocusedPlaceholderColor = placeholderColor,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),

        singleLine = true,
        shape = RoundedCornerShape(CornerRadiusLarge),
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
