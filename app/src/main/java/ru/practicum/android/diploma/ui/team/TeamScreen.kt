package ru.practicum.android.diploma.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.Heading

@Composable
fun TeamScreen(modifier: Modifier) {
    Heading(modifier, stringResource(R.string.team))
}
