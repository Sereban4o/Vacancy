package ru.practicum.android.diploma.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.R

// YS Display: regular + medium
val YsDisplay = FontFamily(
    Font(R.font.ys_display_regular, FontWeight.Normal), // 400
    Font(R.font.ys_display_medium, FontWeight.Medium), // 500
)

val VacancyTypography = Typography(

    // 🔹 Bold/32 — заголовки экранов "Команда", "Детали вакансии"
    displayLarge = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Bold, // 700
        fontSize = TextSizeDisplayLarge, // 32.sp
        lineHeight = TextLineHeightDisplayLarge, // 38.sp
        letterSpacing = TextLetterSpacingLarge, // 0.sp
    ),

    // 🔹 Medium/22 — крупный текст на деталях вакансии (заголовок карточек, зарплата, название компании, описание, навыки)
    titleMedium = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Medium, // 500
        fontSize = TextSizeLarge, // 22.sp
        lineHeight = TextLineHeightLarge, // 26.sp
        letterSpacing = TextLetterSpacingLarge, // 0.sp
    ),

    // 🔹 Regular/16 — обычный текст (город, описание, второстепенные строки)
    bodyMedium = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Normal, // 400
        fontSize = TextSizeMedium, // 16.sp
        lineHeight = TextLineHeightMedium, // 19.sp
        letterSpacing = TextLetterSpacingMedium, // 0.sp
    ),

    // 🔹 Medium/16 — "члены команды", "требуемый опыт" и подобные Medium/16
    labelMedium = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Medium, // 500
        fontSize = TextSizeMedium, // 16.sp
        lineHeight = TextLineHeightMedium, // 19.sp
        letterSpacing = TextLetterSpacingMedium, // 0.sp
    ),

    // 🔹 Нижний навигатор — Regular/12
    labelSmall = TextStyle(
        fontFamily = YsDisplay,
        fontWeight = FontWeight.Normal, // 400
        fontSize = TextSizeSmall, // 12.sp
        lineHeight = TextLineHeightSmall, // 16.sp
        letterSpacing = TextLetterSpacingSmall, // 0.sp
        textAlign = TextAlign.Center,
    ),
)

// Для совместимости
val AppTypography = VacancyTypography
