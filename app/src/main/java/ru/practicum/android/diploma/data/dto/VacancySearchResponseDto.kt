package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Ответ /vacancies из Practicum Vacancies API.
 */
data class VacancySearchResponseDto(
    @SerializedName("vacancies")
    val vacancies: List<VacancyDto>,

    @SerializedName("found")
    val found: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("page")
    val page: Int
)
