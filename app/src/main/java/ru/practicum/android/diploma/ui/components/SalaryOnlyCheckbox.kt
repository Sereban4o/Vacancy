package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.BoxBackground

@Composable
fun SalaryOnlyCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.hide_without_salary),
            style = MaterialTheme.typography.bodyMedium, // Regular/16
            color = MaterialTheme.colorScheme.onBackground, // день чёрный, ночь белый
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))

        val iconRes = if (checked) {
            R.drawable.check_box_on__24px
        } else {
            R.drawable.check_box_off__24px
        }

        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = BoxBackground, // тот же синий, что и в остальных контролах
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SalaryOnlyCheckboxCheckedPreview() {
    MaterialTheme {
        SalaryOnlyCheckbox(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SalaryOnlyCheckboxUncheckedPreview() {
    MaterialTheme {
        SalaryOnlyCheckbox(
            checked = false,
            onCheckedChange = {}
        )
    }
}
