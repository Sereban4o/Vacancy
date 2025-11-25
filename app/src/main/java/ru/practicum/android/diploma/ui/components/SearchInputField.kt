package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.CornerRadiusLarge

@Composable
fun SearchInputField(
    query: String,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = query,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.vacancy_text_placeholder),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                ActionIcon(
                    iconRes = R.drawable.ic_clear,
                    onClick = { onClearClick() }
                )
            } else {
                ActionIcon(
                    iconRes = R.drawable.ic_search,
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = colorResource(R.color.blue),
        ),
        singleLine = true,
        shape = RoundedCornerShape(CornerRadiusLarge),
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchInputFieldPreview() {
    AppTheme {
        SearchInputField(
            query = "",
            onTextChanged = {},
            onClearClick = {}
        )
    }
}
