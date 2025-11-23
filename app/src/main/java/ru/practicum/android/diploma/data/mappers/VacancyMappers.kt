package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

/**
 * Маппер одного VacancyDto в доменную Vacancy.
 */
fun VacancyDto.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        title = name,
        company = employer?.name.orEmpty()
        // если потом добавим зарплату/город в доменную модель — сюда же добавим
    )
}

/**
 * Маппер всего ответа поиска в доменную модель.
 */
fun VacancySearchResponseDto.toDomain(): VacanciesSearchResult {
    return VacanciesSearchResult(
        vacancies = vacancies.map { it.toDomain() },
        page = page,
        pages = pages
    )
}
