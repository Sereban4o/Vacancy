package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    error = ErrorColor,
    onError = OnErrorColor,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = ErrorColor,
    onError = OnErrorColor,
)

@Composable
fun VacancyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = VacancyTypography, // вместо MaterialTheme.typography
        content = content,
    )
}

// Alias под "AppTheme" из Issue 7.3
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    VacancyTheme(darkTheme = darkTheme, content = content)
}
