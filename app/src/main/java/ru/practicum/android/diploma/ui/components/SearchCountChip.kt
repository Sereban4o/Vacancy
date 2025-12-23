package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R

@Composable
fun SearchCountChip(
    total: Int,
    modifier: Modifier = Modifier
) {
    // Обёртка, чтобы чип был ПО ЦЕНТРУ и фон вокруг оставался прозрачным
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.tertiary, // #3772E7 из темы
        ) {
            Text(
                text = stringResource(R.string.vacancy_search_found_format, total),
                // "Найдено %d вакансий"
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary // белый текст
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchCountChipPreview() {
    MaterialTheme {
        SearchCountChip(
            total = 42
        )
    }
}
