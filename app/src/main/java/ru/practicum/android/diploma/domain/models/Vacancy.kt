package ru.practicum.android.diploma.domain.models

/**
 * Модель тестовой вакансии для Domain-слоя.
 * Потом можно заменить/расширить под реальные данные API.
 */
data class Vacancy(
    val id: String,
    val title: String,
    val company: String,
    val logoUrl: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val city: String?,
)
