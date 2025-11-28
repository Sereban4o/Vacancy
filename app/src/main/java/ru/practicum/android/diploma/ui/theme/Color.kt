@file:Suppress("MagicNumber")

package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.graphics.Color

// ------- Light theme -------
val PrimaryLight: Color = Color(0xFF3772E7)
// основной синий Practicum

val OnPrimaryLight: Color = Color(0xFFFFFFFF)

val SecondaryLight: Color = Color(0xFFE6E8EB)
// светло-серый (чипы, бордеры, фон)

val OnSecondaryLight: Color = Color(0xFF000000)

val BackgroundLight: Color = Color(0xFFFFFFFF)
// фон экрана

val OnBackgroundLight: Color = Color(0xFF000000)

val SurfaceLight: Color = Color(0xFFFFFFFF)
// карточки, панели

val OnSurfaceLight: Color = Color(0xFF000000)

val SurfaceVariantLight: Color = Color(0xFFF3F3F7)
// доп. поверхности если нужно

val OnSurfaceVariantLight: Color = Color(0xFF7A7A7A)
// вторичный текст

// Бордеры и разделители
val OutlineLight: Color = Color(0xFFE6E8EB)
// именно твой цвет обводки

// ------- Dark theme -------
val PrimaryDark: Color = Color(0xFF3772E7)
// тот же синий в тёмной теме

val OnPrimaryDark: Color = Color(0xFFFFFFFF)

val SecondaryDark: Color = Color(0xFF2F3336)
// тёмно-серый вторичный

val OnSecondaryDark: Color = Color(0xFFFFFFFF)

val BackgroundDark: Color = Color(0xFF1A1B22)
val OnBackgroundDark: Color = Color(0xFFFFFFFF)

val SurfaceDark: Color = Color(0xFF222327)
val OnSurfaceDark: Color = Color(0xFFFFFFFF)

val SurfaceVariantDark: Color = Color(0xFF2F3336)
val OnSurfaceVariantDark: Color = Color(0xFFCCCCCC)

// Бордеры в тёмной теме
val OutlineDark: Color = Color(0xFF2F3336)

// ------- Error -------
val ErrorColor: Color = Color(0xFFB00020)
val OnErrorColor: Color = Color(0xFFFFFFFF)

// ------- Custom component colors -------

// Цвет фона карточки компании в деталях
val CompanyCardBackgroundColor: Color = Color(0xFFE6E8EB)

// Синий чип "Найдено N вакансий"
val ResultsChipBlue: Color = Color(0xFF3772E7)
val OnResultsChipBlue: Color = Color(0xFFFFFFFF)

// ------- Search field -------

// цвет текста в поле ввода (одинаковый для светлой и тёмной тем)
val SearchFieldTextColor: Color = Color(0xFF1A1B22)

// фон поля ввода — день
val SearchFieldBackgroundLight: Color = Color(0xFFE6E8EB)

// фон поля ввода — ночь
val SearchFieldBackgroundDark: Color = Color(0xFFAEAFB4)

// ------- Common text color -------

// текст ДНЁМ (чёрный)
val TextColorLight: Color = Color(0xFF1A1B22)

// текст НОЧЬЮ (белый)
val TextColorDark: Color = Color(0xFFFDFDFD)
