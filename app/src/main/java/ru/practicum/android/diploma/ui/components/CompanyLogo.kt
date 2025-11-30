package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.CornerRadiusMedium
import ru.practicum.android.diploma.ui.theme.DividerColor

/**
 * Картинка логотипа работодателя.
 * Загружается по URL, с плейсхолдером и fallback.
 */
@Composable
fun CompanyLogo(
    logoUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val shape = RoundedCornerShape(CornerRadiusMedium)

    // 👇 фон под логотипом:
    //  - светлая тема → прозрачный (как в макете)
    //  - тёмная тема → лёгкий полупрозрачный серый, чтобы видно было чёрные логотипы
    val bgColor: Color = if (isDark) {
        Color.White.copy(alpha = 0.2f) // 0.04f..0.1f
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .border(1.dp, DividerColor, shape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(logoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.padding(4.dp),
            contentScale = ContentScale.Inside,
            placeholder = painterResource(R.drawable.ic_company_placeholder),
            fallback = painterResource(R.drawable.ic_company_placeholder),
            error = painterResource(R.drawable.ic_company_placeholder)
        )
    }
}
