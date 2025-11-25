package ru.practicum.android.diploma.ui.vacancydetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VacancyDetailsErrorPlaceholder(
    modifier: Modifier = Modifier,
    isNetworkError: Boolean,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (isNetworkError) "Нет подключения к интернету"
                else "Ошибка загрузки вакансии",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onRetryClick) {
                Text("Повторить")
            }
        }
    }
}
