package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
    // если логотипа нет вообще — сразу плейсхолдер
    if (logoUrl.isNullOrBlank()) {
        PlaceholderLogo(modifier)
        return
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(logoUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_company_placeholder),
        error = painterResource(R.drawable.ic_company_placeholder),
        fallback = painterResource(R.drawable.ic_company_placeholder),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(CornerRadiusMedium))
    )
}

@Composable
private fun PlaceholderLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CornerRadiusMedium))
            .background(androidx.compose.ui.graphics.Color.LightGray)
    ) {
        AsyncImage(
            model = painterResource(R.drawable.ic_company_placeholder),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
