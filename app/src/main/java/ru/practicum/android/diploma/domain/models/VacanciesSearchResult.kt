package ru.practicum.android.diploma.domain.models

/**
 * Результат поиска вакансий в Domain-слое.
 *
 * @param vacancies список найденных вакансий
 * @param page номер текущей страницы
 * @param pages общее количество страниц (или максимум доступной)
 */
data class VacanciesSearchResult(
    val vacancies: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val found: Int
)
