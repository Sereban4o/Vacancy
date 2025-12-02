package ru.practicum.android.diploma.domain.models

/**
 * Является ли фильтр "непустым" с точки зрения ТЗ Epic 4.2.
 * (Epic 5 — страна/регион можно добавить сюда позже).
 */
fun FilterSettings.isActiveForSearch(): Boolean {
    // ЗП считается активной, если задана и > 0
    val hasSalary = salaryFrom != null && salaryFrom > 0

    // Чекбокс активен только если true.
    // false по ТЗ = как будто фильтра нет.
    val hasWithSalaryOnly = withSalaryOnly   // true → учитываем, false → нет

    // Отрасль активна, если есть объект и у него непустой id
    val hasIndustry = industry?.id?.isNotBlank() == true

    // Пока только эти три (Epic 4).
    // Позже можно добавить страну/регион (Epic 5).
    return hasSalary || hasWithSalaryOnly || hasIndustry
}

/**
 * Маппинг сохранённых настроек фильтра в модель,
 * которая идёт в репозиторий / API.
 *
 * Здесь уже зашита логика ТЗ:
 *  - salaryFrom == null или 0  → не учитываем
 *  - withSalaryOnly == false   → не влияет на подсветку, но в запрос можно передать false
 *  - пустая отрасль            → не учитываем
 *  - region / country — пока только region.id (Epic 5 можно дорасти позже)
 */
fun FilterSettings.toSearchFilters(): SearchFilters {
    val effectiveSalary = salaryFrom?.takeIf { it > 0 }
    val effectiveIndustryId = industry?.id?.takeIf { it.isNotBlank() }
    val effectiveRegionId = region?.id?.takeIf { it.isNotBlank() }

    return SearchFilters(
        regionId = effectiveRegionId,
        industryId = effectiveIndustryId,
        salaryFrom = effectiveSalary,
        onlyWithSalary = withSalaryOnly
    )
}

