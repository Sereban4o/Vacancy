package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.CornerRadiusMedium

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

    val finalModifier = modifier
        .clip(RoundedCornerShape(CornerRadiusMedium))

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(logoUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = finalModifier,
        contentScale = ContentScale.Inside,
        placeholder = painterResource(R.drawable.ic_company_placeholder), // при загрузке
        fallback = painterResource(R.drawable.ic_company_placeholder), // если null
        error = painterResource(R.drawable.ic_company_placeholder) // если ошибка
    )
}
