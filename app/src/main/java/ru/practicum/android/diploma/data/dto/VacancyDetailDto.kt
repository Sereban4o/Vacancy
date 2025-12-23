package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO полных данных вакансии из Practicum Vacancies API.
 *
 * Используется:
 *  - в списке вакансий (GET /vacancies)
 *  - в деталях вакансии (GET /vacancies/{id})
 */
data class VacancyDetailDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("salary")
    val salary: SalaryDto?,

    @SerializedName("address")
    val address: AddressDto?,

    @SerializedName("experience")
    val experience: ExperienceDto?,

    @SerializedName("schedule")
    val schedule: ScheduleDto?,

    @SerializedName("employment")
    val employment: EmploymentDto?,

    @SerializedName("contacts")
    val contacts: ContactsDto?,

    @SerializedName("employer")
    val employer: EmployerDto,

    @SerializedName("area")
    val area: FilterAreaDto,

    @SerializedName("skills")
    val skills: List<String>?,

    @SerializedName("url")
    val url: String,

    @SerializedName("industry")
    val industry: FilterIndustryDto
)

/**
 * Зарплата вакансии.
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
 */
data class AddressDto(
    @SerializedName("city")
    val city: String?,

    @SerializedName("street")
    val street: String?,

    @SerializedName("building")
    val building: String?,

    // Полный адрес приходит в поле "raw"
    @SerializedName("raw")
    val fullAddress: String?
)

/**
 * Опыт работы.
 */
data class ExperienceDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * График работы.
 */
data class ScheduleDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * Тип занятости.
 */
data class EmploymentDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * Контактная информация.
 */
data class ContactsDto(
    @SerializedName("id")
    val id: String,

    // Комментарий к контактам (например: "отдел кадров")
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phones")
    val phones: List<PhoneDto>?
)

/**
 * Один телефонный номер.
 */
data class PhoneDto(
    @SerializedName("comment")
    val comment: String?,

    @SerializedName("formatted")
    val formatted: String
)

/**
 * Информация о работодателе.
 */
data class EmployerDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("logo")
    val logo: String?
)

/**
 * Регион.
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
 * Отрасль компании.
 */
data class FilterIndustryDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)
