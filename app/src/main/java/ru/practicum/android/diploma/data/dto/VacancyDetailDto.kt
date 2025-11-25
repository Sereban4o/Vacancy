package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Полная модель вакансии (VacancyDetail) из Practicum Vacancies API.
 *
 * Используется:
 * - в ответе поиска: GET /vacancies (как элемент массива vacancies в VacancyResponse)
 * - в деталях вакансии: GET /vacancies/{id}
 *
 * Соответствует описанию VacancyDetail в документации:
 * VacancyDetail {
 *   id: string
 *   name: string
 *   description: string
 *   salary: Salary?
 *   address: Address?
 *   experience: Experience?
 *   schedule: Schedule?
 *   employment: Employment?
 *   contacts: Contacts?
 *   employer: Employer
 *   area: FilterArea
 *   skills: Array[string]
 *   url: string
 *   industry: FilterIndustry
 * }
 */
data class VacancyDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("salary") val salary: SalaryDto?,
    @SerializedName("address") val address: AddressDto?,
    @SerializedName("experience") val experience: ExperienceDto?,
    @SerializedName("schedule") val schedule: ScheduleDto?,
    @SerializedName("employment") val employment: EmploymentDto?,
    @SerializedName("contacts") val contacts: ContactsDto?,
    @SerializedName("employer") val employer: EmployerDto,
    @SerializedName("area") val area: FilterAreaDto,
    @SerializedName("skills") val skills: List<String>?,
    @SerializedName("url") val url: String,
    @SerializedName("industry") val industry: FilterIndustryDto
)

/**
 * Уровень зарплаты в деталях вакансии.
 *
 * Salary {
 *   from: integer?
 *   to: integer?
 *   currency: string?
 * }
 */
data class SalaryDto(
    @SerializedName("from")
    val from: Int?,

    @SerializedName("to")
    val to: Int?,

    @SerializedName("currency")
    val currency: String?
)

/**
 * Адрес компании / места работы.
 *
 * Address {
 *   city: string
 *   street: string
 *   building: string
 *   fullAddress: string
 * }
 */
data class AddressDto(
    @SerializedName("city")
    val city: String?,

    @SerializedName("street")
    val street: String?,

    @SerializedName("building")
    val building: String?,

    @SerializedName("raw")
    val fullAddress: String? // берём поле raw
)

/**
 * Опыт работы.
 *
 * Experience {
 *   id: string
 *   name: string
 * }
 */
data class ExperienceDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * График работы.
 *
 * Schedule {
 *   id: string
 *   name: string
 * }
 */
data class ScheduleDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * Тип занятости.
 *
 * Employment {
 *   id: string
 *   name: string
 * }
 */
data class EmploymentDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * Контактные данные.
 *
 * Contacts {
 *   id: string
 *   name: string        // комментарий к контактам (например, "отдел кадров")
 *   email: string
 *   phone: Array[string]
 * }
 */
data class ContactsDto(
    @SerializedName("id")
    val id: String,

    // комментарий, который по заданию надо показывать как "комментарий к номеру"
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phones")
    val phones: List<PhoneDto>? // 🔴 phones, не phone
)

data class PhoneDto(
    @SerializedName("comment")
    val comment: String?,

    @SerializedName("formatted")
    val formatted: String
)

/**
 * Работодатель.
 *
 * Employer {
 *   id: string
 *   name: string
 *   logo: string
 * }
 */
data class EmployerDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    // URL логотипа компании (может отсутствовать)
    @SerializedName("logo")
    val logo: String?
)

/**
 * Регион (FilterArea) для Practicum API.
 *
 * FilterArea {
 *   id: integer
 *   name: string
 *   parentId: integer
 *   areas: Array[FilterArea]
 * }
 */
data class FilterAreaDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("parentId")
    val parentId: String?,

    @SerializedName("areas")
    val areas: List<FilterAreaDto>
)

/**
 * Отрасль (FilterIndustry) для Practicum API.
 *
 * FilterIndustry {
 *   id: integer
 *   name: string
 * }
 */
data class FilterIndustryDto(
    @SerializedName("id")
    val id: String, // 🔴 было Int

    @SerializedName("name")
    val name: String
)
