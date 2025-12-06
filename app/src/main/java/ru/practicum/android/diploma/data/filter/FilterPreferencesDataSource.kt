package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterPreferencesDataSource {

    suspend fun readFilterSettings(): FilterSettings?

    suspend fun writeFilterSettings(settings: FilterSettings)

    suspend fun clearFilterSettings()
}
