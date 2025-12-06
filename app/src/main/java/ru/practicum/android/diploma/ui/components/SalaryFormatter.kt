package ru.practicum.android.diploma.ui.components

import android.annotation.SuppressLint
import android.content.res.Resources
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

// Форматтер чисел с разделением на разряды.
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

// Оверрайды для валют, которые хотим показывать красиво, а не кодом.
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

// Вынесли отдельную функцию, чтобы не плодить вложенность в mapCurrencyCodeToDisplay.
private fun resolveFromCurrency(upperCode: String): String {
    val currency = availableCurrencies[upperCode] ?: return upperCode
    val symbol = currency.symbol
    return if (symbol.equals(upperCode, ignoreCase = true)) {
        upperCode
    } else {
        symbol
    }
}

/**
 * Маппинг кода валюты из API в человекочитаемое отображение для UI.
 */
private fun mapCurrencyCodeToDisplay(code: String?): String {
    val upperCode = code?.uppercase(Locale.ROOT).orEmpty()

    return when {
        upperCode.isEmpty() ->
            ""

        currencySymbolOverrides.containsKey(upperCode) ->
            currencySymbolOverrides.getValue(upperCode)

        else ->
            resolveFromCurrency(upperCode)
    }
}

/**
 * Форматирование зарплаты по правилам ТЗ:
 * - "от XX", "до XX", "от XX до XX"
 * - "зарплата не указана", если нет значений
 * - валюта отображается всегда (если пришёл код)
 * - числа с разбиением на разряды
 *
 * Все текстовые части вынесены в strings.xml.
 */
fun formatSalary(
    salaryFrom: Int?,
    salaryTo: Int?,
    currencyCode: String?,
    resources: Resources
): String {
    val currency = mapCurrencyCodeToDisplay(currencyCode)

    val fromStr = salaryFrom?.let { formatNumber(it) }
    val toStr = salaryTo?.let { formatNumber(it) }

    val raw = when {
        fromStr != null && toStr != null -> {
            resources.getString(
                R.string.salary_from_to,
                fromStr,
                toStr,
                currency
            )
        }

        fromStr != null -> {
            resources.getString(
                R.string.salary_from,
                fromStr,
                currency
            )
        }

        toStr != null -> {
            resources.getString(
                R.string.salary_to,
                toStr,
                currency
            )
        }

        else -> {
            resources.getString(R.string.salary_not_specified)
        }
    }

    // На случай, если валюта пустая и образуется лишний пробел в конце.
    return raw.trimEnd()
}

/**
 * Обёртка для доменной модели Vacancy.
 */
fun formatSalary(vacancy: Vacancy, resources: Resources): String {
    return formatSalary(
        salaryFrom = vacancy.salaryFrom,
        salaryTo = vacancy.salaryTo,
        currencyCode = vacancy.currency,
        resources = resources
    )
}
