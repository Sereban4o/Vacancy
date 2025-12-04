package ru.practicum.android.diploma.ui.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.ActionIcon
import ru.practicum.android.diploma.ui.theme.PrimaryLight
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundDark
import ru.practicum.android.diploma.ui.theme.SearchFieldBackgroundLight
import ru.practicum.android.diploma.ui.theme.SearchFieldTextColor
import ru.practicum.android.diploma.ui.theme.TextColorDark

@Composable
fun ExpectedSalaryField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    isWithSalaryOnly: Boolean,
    modifier: Modifier = Modifier
) {
    val (fieldBackground, placeholderColor) = rememberSalaryFieldColors()
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(51.dp) // высота по макету
            .background(
                color = fieldBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = { new ->
                if (new.all { it.isDigit() }) onValueChange(new)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state -> isFocused = state.isFocused },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = SearchFieldTextColor
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.tertiary),
            decorationBox = { innerTextField ->
                SalaryFieldContent(
                    value = value,
                    isFocused = isFocused,
                    placeholderColor = placeholderColor,
                    onClear = onClear,
                    isWithSalaryOnly = isWithSalaryOnly,
                    innerTextField = innerTextField
                )
            }
        )
    }
}

@Composable
private fun rememberSalaryFieldColors(): Pair<Color, Color> {
    val isDark = isSystemInDarkTheme()

    val fieldBackground = if (isDark) {
        SearchFieldBackgroundDark // ночь: #AEAFB4
    } else {
        SearchFieldBackgroundLight // день: #E6E8EB
    }

    val placeholderColor = if (isDark) {
        TextColorDark // ночь: белый
    } else {
        SearchFieldBackgroundDark // день: #AEAFB4
    }

    return fieldBackground to placeholderColor
}

@Composable
private fun SalaryFieldContent(
    value: String,
    isFocused: Boolean,
    placeholderColor: Color,
    onClear: () -> Unit,
    isWithSalaryOnly: Boolean,
    innerTextField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            SalaryTitle(
                isFocused = isFocused,
                hasValue = value.isNotEmpty(),
                placeholderColor = placeholderColor,
                isWithSalaryOnly = isWithSalaryOnly
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.salary_hint),
                        style = MaterialTheme.typography.bodyMedium,
                        color = placeholderColor
                    )
                }
                innerTextField()
            }
        }

        if (value.isNotEmpty()) {
            Spacer(Modifier.height(0.dp))
            ActionIcon(
                iconRes = R.drawable.ic_clear_24,
                onClick = onClear,
                tint = SearchFieldTextColor
            )
        }
    }
}

@Composable
private fun SalaryTitle(
    isFocused: Boolean,
    hasValue: Boolean,
    placeholderColor: Color,
    isWithSalaryOnly: Boolean
) {
    val color = when {
        isWithSalaryOnly -> {
            // 🔥 чекбокс включен → всегда чёрный (#1A1B22)
            SearchFieldTextColor
        }
        isFocused || hasValue -> {
            // обычное поведение → синий при фокусе / наличии значения
            PrimaryLight
        }
        else -> {
            placeholderColor
        }
    }

    Text(
        text = stringResource(R.string.expected_salary),
        style = MaterialTheme.typography.labelSmall,
        color = color
    )
}
