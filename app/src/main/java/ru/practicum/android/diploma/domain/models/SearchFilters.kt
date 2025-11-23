package ru.practicum.android.diploma.domain.models

/**
 * Единый контракт фильтров для поискового запроса вакансий.
 *
 * @param regionId идентификатор региона (как его понимает API)
 * @param regionId идентификатор индустрии (как его понимает API)
 * @param salaryFrom нижняя граница зарплаты (в целевой валюте API)
 * @param onlyWithSalary только вакансии с указанной зарплатой
 */
data class SearchFilters(
    val regionId: String?,
    val industryId: String?,
    val salaryFrom: Int?,
    val onlyWithSalary: Boolean
    // remoteOnly и т.п. можно вообще убрать, если не нужны
)

