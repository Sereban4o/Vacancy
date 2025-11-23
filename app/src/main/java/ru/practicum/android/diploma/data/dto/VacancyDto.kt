package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Краткая модель вакансии из search-эндпоинта HH.
 *
 * Мы берём только те поля, которые реально нужны для поиска/листа.
 * Остальное можно добавить позже.
 */
data class VacancyDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("area")
    val area: AreaDto?,

    @SerializedName("salary")
    val salary: SalaryDto?,

    @SerializedName("employer")
    val employer: EmployerDto?,

    @SerializedName("snippet")
    val snippet: SnippetDto?,

    @SerializedName("published_at")
    val publishedAt: String?,

    @SerializedName("alternate_url")
    val alternateUrl: String?
)

/**
 * Регион (город/страна).
 */
data class AreaDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)

/**
 * Зарплата в кратком ответе.
 */
data class SalaryDto(
    @SerializedName("from")
    val from: Int?,

    @SerializedName("to")
    val to: Int?,

    @SerializedName("currency")
    val currency: String?,

    @SerializedName("gross")
    val gross: Boolean?
)

/**
 * Работодатель.
 */
data class EmployerDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?
)

/**
 * Ссылки на логотипы разных размеров.
 */
data class LogoUrlsDto(
    @SerializedName("original")
    val original: String?,

    @SerializedName("90")
    val small: String?,

    @SerializedName("240")
    val large: String?
)

/**
 * Краткие фрагменты требований/обязанностей.
 */
data class SnippetDto(
    @SerializedName("requirement")
    val requirement: String?,

    @SerializedName("responsibility")
    val responsibility: String?
)
