package ru.practicum.android.diploma.ui.components

import android.annotation.SuppressLint
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.NumberFormat
import java.util.Currency
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

// Все валюты из стандартной библиотеки: код -> Currency
private val availableCurrencies: Map<String, Currency> =
    Currency.getAvailableCurrencies().associateBy { it.currencyCode.uppercase(Locale.ROOT) }

// Оверрайды для валют, которые мы хотим показывать не как код.
private val currencySymbolOverrides: Map<String, String> = mapOf(
    "RUR" to "₽",
    "RUB" to "₽",
    "USD" to "$",
    "EUR" to "€",
    "GBP" to "£",
    "HKD" to "HK$",
    "SEK" to "kr",
    "SGD" to "S$"
)

/**
 * Маппинг кода валюты из API в человекочитаемое отображение для UI.
 *
 * Укладываемся в ограничения detekt:
 *  - без try/catch
 *  - не больше 2 return (здесь всего 1).
 */
private fun mapCurrencyCodeToDisplay(code: String?): String {
    val upperCode = code?.uppercase(Locale.ROOT).orEmpty()

    val result = if (upperCode.isEmpty()) {
        ""
    } else {
        val override = currencySymbolOverrides[upperCode]
        if (override != null) {
            override
        } else {
            val currency = availableCurrencies[upperCode]
            if (currency != null) {
                val symbol = currency.symbol
                if (symbol.equals(upperCode, ignoreCase = true)) {
                    // Если символ совпадает с кодом — показываем код
                    // (или можно добавить в currencySymbolOverrides при необходимости)
                    upperCode
                } else {
                    symbol
                }
            } else {
                upperCode
            }
        }
    }

    return result
}

/**
 * Форматирование зарплаты по правилам ТЗ:
 * - "от XX", "до XX", "от XX до XX"
 * - "зарплата не указана", если нет значений
 * - валюта отображается всегда (если пришёл код)
 * - числа с разбиением на разряды
 */
fun formatSalary(
    salaryFrom: Int?,
    salaryTo: Int?,
    currencyCode: String?
): String {
    val currency = mapCurrencyCodeToDisplay(currencyCode)

    val fromStr = salaryFrom?.let { formatNumber(it) }
    val toStr = salaryTo?.let { formatNumber(it) }

    return when {
        fromStr != null && toStr != null -> "от $fromStr до $toStr $currency"
        fromStr != null -> "от $fromStr $currency"
        toStr != null -> "до $toStr $currency"
        else -> "зарплата не указана"
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
