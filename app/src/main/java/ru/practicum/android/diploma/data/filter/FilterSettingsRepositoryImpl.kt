package ru.practicum.android.diploma.data.filter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    private val dataSource: FilterPreferencesDataSource
) : FilterSettingsRepository {

    override suspend fun getFilterSettings(): FilterSettings =
        withContext(Dispatchers.IO) {
            dataSource.readFilterSettings() ?: FilterSettings()
        }

    override suspend fun saveFilterSettings(settings: FilterSettings) =
        withContext(Dispatchers.IO) {
            dataSource.writeFilterSettings(settings)
        }

    override suspend fun clearFilterSettings() =
        withContext(Dispatchers.IO) {
            dataSource.clearFilterSettings()
        }
}
