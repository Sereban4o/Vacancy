package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.VacancySearchResponseDto
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetails

/**
 * Маппер одного VacancyDto в доменную Vacancy.
 */
fun VacancyDetailDto.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        title = name,
        company = employer.name,
        logoUrl = employer.logo,
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
        currency = salary?.currency
    )
}

/**
 * Маппер всего ответа поиска в доменную модель.
 */
fun VacancySearchResponseDto.toDomain(): VacanciesSearchResult {
    return VacanciesSearchResult(
        vacancies = vacancies.map { it.toDomain() },
        page = page,
        pages = pages,
        found = found
    )
}

// Для экрана деталей
fun VacancyDetailDto.toDomainDetails(): VacancyDetails =
    VacancyDetails(
        id = id,
        title = name,
        description = description,
        companyName = employer.name,
        logoUrl = employer.logo,
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
        currency = salary?.currency,
        address = address?.fullAddress,
        region = area.name,
        experience = experience?.name,
        schedule = schedule?.name,
        employment = employment?.name,
        skills = skills ?: emptyList(),
        contacts = contacts?.toDomain(),
        vacancyUrl = url
    )

fun ContactsDto.toDomain(): VacancyContacts =
    VacancyContacts(
        email = email,
        phones = phones?.map { it.formatted } ?: emptyList(),
        comment = name
    )
