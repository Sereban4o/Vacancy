package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Ответ /vacancies из Practicum Vacancies API.
 *
 * VacancyResponse {
 *   found: integer
 *   pages: integer
 *   page: integer
 *   vacancies: Array[VacancyDetail]
 * }
 */
data class VacancySearchResponseDto(

    @SerializedName("items") // 🔴 было "vacancies" но по факту там items
    val vacancies: List<VacancyDetailDto>,

    @SerializedName("found")
    val found: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("page")
    val page: Int
)
