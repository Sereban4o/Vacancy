package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.Heading

@Composable
fun MainScreen(
    modifier: Modifier,
    onOpenMockVacancy: () -> Unit,
    onOpenRealVacancy: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Heading(modifier, stringResource(R.string.mainHeading))

        Button(
            onClick = onOpenMockVacancy,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Открыть мок-вакансию")
        }

        Button(
            onClick = onOpenRealVacancy,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Открыть реальную вакансию (API)")
        }
    }
}
