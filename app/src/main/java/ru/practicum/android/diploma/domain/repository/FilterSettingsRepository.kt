package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterSettingsRepository {

    /** Единоразовое чтение (там, где нужно suspend-API) */
    suspend fun getFilterSettings(): FilterSettings

    /** Сохранение настроек */
    suspend fun saveFilterSettings(settings: FilterSettings)

    /** Полный сброс фильтров */
    suspend fun clearFilterSettings()
}
