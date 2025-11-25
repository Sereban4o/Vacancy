package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.ActionIcon
import ru.practicum.android.diploma.ui.components.Heading

@Composable
fun MainScreen(modifier: Modifier) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Heading(Modifier, stringResource(R.string.mainHeading))
            Spacer(modifier = Modifier.weight(1f))
            ActionIcon(
                iconRes = R.drawable.ic_filter,
                onClick = {}
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(Modifier)
}
