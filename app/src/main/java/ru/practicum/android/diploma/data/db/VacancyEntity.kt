package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Минимальная сущность для Room.
 * Потом дополним полями по реальному API.
 */
@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val title: String? = null,
    val company: String? = null
)
