package ru.practicum.android.diploma.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val VacancyTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = TextSizeMedium,
        lineHeight = TextLineHeightMedium,
        letterSpacing = TextLetterSpacingMedium,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextSizeLarge,
        lineHeight = TextLineHeightLarge,
        letterSpacing = TextLetterSpacingLarge,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = TextSizeSmall,
        lineHeight = TextLineHeightSmall,
        letterSpacing = TextLetterSpacingSmall,
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = TextSizeDisplayLarge,
        lineHeight = TextLineHeightDisplayLarge,
        letterSpacing = TextLetterSpacingLarge,
    )
)

// Для совместимости со старым кодом Issue 3
val AppTypography = VacancyTypography
