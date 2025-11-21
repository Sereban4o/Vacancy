package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

// ----- Новые нормальные имена под Material3 -----

val PrimaryLight = Color(0xFF6200EE)
val OnPrimaryLight = Color(0xFFFFFFFF)

val PrimaryDark = Color(0xFFBB86FC)
val OnPrimaryDark = Color(0xFF000000)

val SecondaryLight = Color(0xFF03DAC5)
val OnSecondaryLight = Color(0xFF000000)

val SecondaryDark = Color(0xFF03DAC5)
val OnSecondaryDark = Color(0xFF000000)

val BackgroundLight = Color(0xFFFFFFFF)
val BackgroundDark = Color(0xFF121212)

val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFF1E1E1E)

val ErrorColor = Color(0xFFB00020)
val OnErrorColor = Color(0xFFFFFFFF)

// ----- Совместимость с Issue 3 (старые имена-алиасы) -----

// В Issue 3 были такие константы — оставляем, но строим их из новых
val PurplePrimary = PrimaryLight
val PurpleSecondary = SecondaryLight
// BackgroundLight / BackgroundDark уже есть, имена совпали
