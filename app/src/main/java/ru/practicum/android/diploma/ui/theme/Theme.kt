package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// -----------------------------
// LIGHT / DARK COLOR SCHEMES
// -----------------------------
// Используем новые имена из Color.kt (PrimaryLight и т.д.),
// но значения там — из твоей старой зелёно-синей палитры.

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    error = ErrorColor,
    onError = OnErrorColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    error = ErrorColor,
    onError = OnErrorColor,
)

// -----------------------------
// ОСНОВНАЯ COMPOSE-ТЕМА
// -----------------------------
// Новый "канонический" вход: VacancyTheme (из Issue 7).

@Composable
fun VacancyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = VacancyTypography,
        content = content,
    )
}

// -----------------------------
// АЛИАСЫ ДЛЯ СОВМЕСТИМОСТИ
// -----------------------------
// 1) AppTheme — как в Issue 7.3
// 2) PracticumAndroidDiplomaTheme — как в Issue 3

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    VacancyTheme(darkTheme = darkTheme, content = content)
}

@Composable
fun PracticumAndroidDiplomaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    VacancyTheme(darkTheme = darkTheme, content = content)
}
