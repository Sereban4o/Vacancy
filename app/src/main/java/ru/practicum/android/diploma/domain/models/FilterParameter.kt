package ru.practicum.android.diploma.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class FilterParameter(
    val id: String,
    val name: String
)
