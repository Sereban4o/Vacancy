package ru.practicum.android.diploma.data.filter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    private val dataSource: FilterPreferencesDataSource
) : FilterSettingsRepository {

    private val _settingsFlow = MutableStateFlow(FilterSettings())

    override suspend fun getFilterSettings(): FilterSettings =
        withContext(Dispatchers.IO) {
            _settingsFlow.value
        }

    override suspend fun saveFilterSettings(settings: FilterSettings) =
        withContext(Dispatchers.IO) {
            dataSource.writeFilterSettings(settings)
            _settingsFlow.value = settings // 🔹 уведомляем всех подписчиков
        }

    override suspend fun clearFilterSettings() =
        withContext(Dispatchers.IO) {
            dataSource.clearFilterSettings()
            _settingsFlow.value = FilterSettings() // 🔹 сбросили для всех
        }
}
