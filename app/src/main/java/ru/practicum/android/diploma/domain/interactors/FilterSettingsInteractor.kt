package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsInteractor(
    private val repository: FilterSettingsRepository
) {

    suspend fun getFilterSettings(): FilterSettings =
        repository.getFilterSettings()

    suspend fun saveFilterSettings(settings: FilterSettings) =
        repository.saveFilterSettings(settings)

    suspend fun clearFilterSettings() =
        repository.clearFilterSettings()
}
