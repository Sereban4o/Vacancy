package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,

    // чип и прочее, если используешь tertiary
    tertiary = ResultsChipBlue,
    onTertiary = OnResultsChipBlue,

    background = BackgroundLight,
    onBackground = TextColorLight,
    surface = SurfaceLight,
    onSurface = TextColorLight,

    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,

    outline = OutlineLight,

    error = ErrorColor,
    onError = OnErrorColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,

    tertiary = ResultsChipBlue,
    onTertiary = OnResultsChipBlue,

    background = BackgroundDark,
    onBackground = TextColorDark,
    surface = SurfaceDark,
    onSurface = TextColorDark,

    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,

    outline = OutlineDark,

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
        typography = VacancyTypography,
        content = content,
    )
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) = VacancyTheme(darkTheme, content)

@Composable
fun PracticumAndroidDiplomaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) = VacancyTheme(darkTheme, content)
