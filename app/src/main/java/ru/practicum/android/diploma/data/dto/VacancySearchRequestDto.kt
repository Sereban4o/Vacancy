package ru.practicum.android.diploma.data.dto

/**
 * DTO-запроса поиска вакансий.
 *
 * Используется в data-слое (RemoteDataSource),
 * маппится из доменных SearchFilters.
 */
data class VacancySearchRequestDto(
    val text: String,
    val page: Int,
    // сохраняем perPage — как внутреннее знание,
    // что мы работаем с "страницами" по 20 элементов
    val perPage: Int,
    // Practicum API принимает одну salary — берём минимальную
    val salaryFrom: Int? = null,
    val onlyWithSalary: Boolean = false,
    // area в Practicum API
    val regionId: String? = null,
    // industry в Practicum API
    val industryId: String? = null,
)
