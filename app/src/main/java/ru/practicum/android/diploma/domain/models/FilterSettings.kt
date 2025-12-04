package ru.practicum.android.diploma.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class FilterSettings(
    val salaryFrom: Int? = null,
    val withSalaryOnly: Boolean = false,
    val industry: FilterParameter? = null,
    val country: FilterParameter? = null,
    val region: FilterParameter? = null,
)
