package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.FilterParameter
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.models.isActiveForSearch

data class FilterUiState(
    val salaryText: String = "",
    val withSalaryOnly: Boolean = false,
    val industry: FilterParameter? = null,
    val country: FilterParameter? = null,
    val region: FilterParameter? = null,
) {
    val workPlaceLabel: String?
        get() = when {
            country == null && region == null -> null
            country != null && region == null -> country.name
            country != null && region != null -> "${country.name}, ${region.name}"
            else -> region?.name
        }

    val hasAnyFilter: Boolean
        get() = FilterSettings(
            salaryFrom = salaryText.toIntOrNull(),
            withSalaryOnly = withSalaryOnly,
            industry = industry,
            country = country,
            region = region
        ).let { it.isActiveForSearch() || it.country != null || it.region != null }
}

/**
 * Маппинг domain → UI
 */
fun FilterSettings.toUiState(): FilterUiState =
    FilterUiState(
        salaryText = salaryFrom?.toString().orEmpty(),
        withSalaryOnly = withSalaryOnly,
        industry = industry,
        country = country,
        region = region
    )
