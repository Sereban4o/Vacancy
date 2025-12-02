package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val companyName: String,
    val logoUrl: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val address: String?,
    val region: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val skills: String,
    val contacts: String,
    val vacancyUrl: String
)
