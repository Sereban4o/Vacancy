package ru.practicum.android.diploma.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.Heading

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier
) {
    Heading(
        text = stringResource(R.string.favorites),
        modifier = modifier
    )
}

