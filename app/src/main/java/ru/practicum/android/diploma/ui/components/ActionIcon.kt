package ru.practicum.android.diploma.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R

@Composable
fun ActionIcon(
    modifier: Modifier = Modifier,
    iconRes: Int,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = "Clear text or Search"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionIconPreview() {
    ActionIcon(
        iconRes = R.drawable.ic_search,
        onClick = {}
    )
}
