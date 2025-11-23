package ru.practicum.android.diploma.domain.models

/**
 * Модель тестовой вакансии для Domain-слоя.
 * Потом можно заменить/расширить под реальные данные API.
 */
data class StubVacancy(
    val id: String,
    val title: String,
    val company: String
)
