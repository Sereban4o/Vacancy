package ru.practicum.android.diploma.ui.team

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R

@Composable
fun TeamScreen(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            stringResource(R.string.team),
            style = TextStyle(
                color = colorResource(R.color.black),
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.ys_display_medium)),
                fontSize = 22.sp
            ), modifier = Modifier.padding(vertical = 19.dp)
        )
    }
}
