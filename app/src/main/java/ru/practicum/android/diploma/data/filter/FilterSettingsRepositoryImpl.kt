package ru.practicum.android.diploma.data.filter

import kotlinx.coroutines.flow.MutableStateFlow
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    private val dataSource: FilterPreferencesDataSource
) : FilterSettingsRepository {

    private val _settingsFlow = MutableStateFlow(FilterSettings())
    // если в интерфейсе появится flow — можно будет отдать его наружу:
    // override val settingsFlow: StateFlow<FilterSettings> get() = _settingsFlow

    override suspend fun getFilterSettings(): FilterSettings {
        val stored: FilterSettings? = dataSource.readFilterSettings()
        val result = stored ?: FilterSettings()
        _settingsFlow.value = result
        return result
    }

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        dataSource.writeFilterSettings(settings)
        _settingsFlow.value = settings
    }

    override suspend fun clearFilterSettings() {
        dataSource.clearFilterSettings()
        _settingsFlow.value = FilterSettings()
    }
}
