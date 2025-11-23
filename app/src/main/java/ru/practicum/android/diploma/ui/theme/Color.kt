@file:Suppress("MagicNumber")

package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

// -----------------------------
// БАЗОВАЯ ПАЛИТРА ДЛЯ COMPOSE
// -----------------------------
// Это "источник правды" для MaterialTheme в Compose.
// Значения синхронизированы с res/values/colors.xml и values-night/colors.xml.

// ------- Light theme -------
val PrimaryLight: Color = Color(0xFF6200EE) // color_primary (light)
val OnPrimaryLight: Color = Color(0xFFFFFFFF) // color_on_primary (light)

val SecondaryLight: Color = Color(0xFF03DAC5) // color_secondary (light)
val OnSecondaryLight: Color = Color(0xFF000000) // color_on_secondary (light)

val BackgroundLight: Color = Color(0xFFFDFDFD) // = white / bottom_nav_background (light)
val OnBackgroundLight: Color = Color(0xFF000000)

val SurfaceLight: Color = Color(0xFFFFFFFF)
val OnSurfaceLight: Color = Color(0xFF000000)

// ------- Dark theme -------
val PrimaryDark: Color = Color(0xFFBB86FC) // color_primary (night)
val OnPrimaryDark: Color = Color(0xFF000000) // color_on_primary (night)

val SecondaryDark: Color = Color(0xFF03DAC5) // color_secondary (night)
val OnSecondaryDark: Color = Color(0xFFFFFFFF) // color_on_secondary (night)

val BackgroundDark: Color = Color(0xFF1A1B22) // = bottom_nav_background (night)
val OnBackgroundDark: Color = Color(0xFFFFFFFF)

val SurfaceDark: Color = Color(0xFF1E1E1E)
val OnSurfaceDark: Color = Color(0xFFFFFFFF)

// ------- Error -------
val ErrorColor: Color = Color(0xFFB00020)
val OnErrorColor: Color = Color(0xFFFFFFFF)

// -----------------------------
// АЛИАСЫ ДЛЯ СОВМЕСТИМОСТИ (старый код)
// -----------------------------
// Если где-то ещё живут старые имена (LightPrimary / DarkPrimary / PurplePrimary и т.д.),
// они продолжают работать, но теперь завязаны на новую палитру.

// Issue 3 style names
val LightPrimary: Color = PrimaryLight
val LightOnPrimary: Color = OnPrimaryLight
val LightSecondary: Color = SecondaryLight
val LightOnSecondary: Color = OnSecondaryLight
val LightBackground: Color = BackgroundLight
val LightOnBackground: Color = OnBackgroundLight

val DarkPrimary: Color = PrimaryDark
val DarkOnPrimary: Color = OnPrimaryDark
val DarkSecondary: Color = SecondaryDark
val DarkOnSecondary: Color = OnSecondaryDark
val DarkBackground: Color = BackgroundDark
val DarkOnBackground: Color = OnBackgroundDark

// Issue 7: PurplePrimary / PurpleSecondary как алиасы к основной палитре
val PurplePrimary: Color = PrimaryLight
val PurpleSecondary: Color = SecondaryLight
