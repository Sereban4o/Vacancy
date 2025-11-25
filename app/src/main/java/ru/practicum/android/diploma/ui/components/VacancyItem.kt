package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.domain.models.Vacancy

/**
 * Одна карточка вакансии в списке.
 *
 * UI-слой, чистый Compose.
 */
@Composable
fun VacancyItem(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            CompanyLogo(
                logoUrl = vacancy.logoUrl,
                modifier = Modifier.size(40.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = vacancy.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = formatSalary(vacancy),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = vacancy.company,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
