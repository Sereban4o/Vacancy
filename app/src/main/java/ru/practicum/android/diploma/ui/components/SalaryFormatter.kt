package ru.practicum.android.diploma.ui.components

import android.annotation.SuppressLint
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.NumberFormat
import java.util.Locale

// Форматтер чисел с разделением на разряды.
// Используем Locale.getDefault(), чтобы не ловить ворнинги про захардкоженную локаль.
@SuppressLint("ConstantLocale")
private val salaryNumberFormat: NumberFormat =
    NumberFormat.getInstance(Locale.getDefault()).apply {
        isGroupingUsed = true // "1000000" -> "1 000 000"
    }

private fun formatNumber(value: Int): String {
    // Некоторые локали используют неразрывный пробел — заменяем на обычный.
    return salaryNumberFormat.format(value).replace('\u00A0', ' ')
}

/**
 * Форматирование зарплаты по правилам ТЗ:
 * - "от XX", "до XX", "от XX до XX"
 * - "зарплата не указана", если нет значений
 * - валюта отображается всегда
 * - числа с разбиением на разряды
 */
fun formatSalary(
    salaryFrom: Int?,
    salaryTo: Int?,
    currencyCode: String?
): String {
    val currency = currencyCode ?: ""
    // по ТЗ валюта "должна отображаться всегда", но если в API null — выводим пустую строку

    val fromStr = salaryFrom?.let { formatNumber(it) }
    val toStr = salaryTo?.let { formatNumber(it) }

    return when {
        fromStr != null && toStr != null -> "от $fromStr до $toStr $currency"
        fromStr != null -> "от $fromStr $currency"
        toStr != null -> "до $toStr $currency"
        else -> "зарплаты не указана"
    }
}

/**
 * Обёртка для уже существующей доменной модели Vacancy,
 * чтобы не переписывать старый код.
 */
fun formatSalary(vacancy: Vacancy): String {
    return formatSalary(
        salaryFrom = vacancy.salaryFrom,
        salaryTo = vacancy.salaryTo,
        currencyCode = vacancy.currency
    )
}
