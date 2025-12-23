package ru.practicum.android.diploma.domain.models

/**
 * Является ли фильтр "непустым" с точки зрения ТЗ Epic 4.2.
 */
fun FilterSettings.isActiveForSearch(): Boolean {
    val hasSalary = salaryFrom != null && salaryFrom > 0
    val hasWithSalaryOnly = withSalaryOnly
    val hasIndustry = industry?.id?.isNotBlank() == true

    // 🔥 Сергей условия: фильтр активен, если выбрана страна или регион
    val hasCountry = country != null
    val hasRegion = region != null

    // 👉 сюда можно будет добавить страну/регион, если ревьюеры будут требовать
    return hasSalary || hasWithSalaryOnly || hasIndustry
        || hasCountry || hasRegion // 🔵 ++(country & area)
}

/**
 * Маппинг FilterSettings -> SearchFilters (то, что реально идёт в API).
 *
 * Теперь:
 *  - если выбран регион → берём region.id
 *  - иначе, если выбрана только страна → берём country.id
 */
fun FilterSettings.toSearchFilters(): SearchFilters {
    val effectiveSalary = salaryFrom?.takeIf { it > 0 }
    val effectiveIndustryId = industry?.id?.takeIf { it.isNotBlank() }

    // ✅ сначала регион, если есть, иначе страна
    val areaSource = region ?: country
    val effectiveAreaId = areaSource
        ?.id
        ?.takeIf { it.isNotBlank() }

    return SearchFilters(
        regionId = effectiveAreaId,
        industryId = effectiveIndustryId,
        salaryFrom = effectiveSalary,
        onlyWithSalary = withSalaryOnly
    )
}
