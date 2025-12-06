package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.domain.models.Vacancy

/**
 * Одна карточка вакансии в списке.
 *
 * UI-слой, чистый Compose.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyItem(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    val resources = LocalContext.current.resources

    // 💰 готовим строку зарплаты один раз на текущие значения
    val salaryText = remember(
        vacancy.salaryFrom,
        vacancy.salaryTo,
        vacancy.currency
    ) {
        formatSalary(vacancy, resources)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background // белый/тёмный по теме
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            CompanyLogo(
                logoUrl = vacancy.logoUrl,
                modifier = Modifier.size(40.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                val titleText = if (vacancy.city.isNullOrBlank()) {
                    vacancy.title
                } else {
                    "${vacancy.title}, ${vacancy.city}"
                }

                // заголовок вакансии
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // компания
                Text(
                    text = vacancy.company,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // зарплата
                Text(
                    text = salaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
