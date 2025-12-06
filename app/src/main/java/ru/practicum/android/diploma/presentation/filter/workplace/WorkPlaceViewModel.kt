package ru.practicum.android.diploma.presentation.filter.workplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings

class WorkPlaceViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkPlaceUiState())
    val uiState: StateFlow<WorkPlaceUiState> = _uiState.asStateFlow()

    /**
     * Загрузка актуальных страны/региона из FilterSettings.
     * Вызывается при заходе на экран (через refresh()).
     */
    fun loadFromFilterSettings() {
        viewModelScope.launch {
            val settings: FilterSettings = filterSettingsInteractor.getFilterSettings()
            _uiState.update {
                it.copy(
                    country = settings.country,
                    region = settings.region
                )
            }
        }
    }

    fun onClearCountry() {
        viewModelScope.launch {
            val current = filterSettingsInteractor.getFilterSettings()
            val updated = current.copy(country = null)
            filterSettingsInteractor.saveFilterSettings(updated)

            _uiState.update { it.copy(country = null) }
        }
    }

    fun onClearRegion() {
        viewModelScope.launch {
            val current = filterSettingsInteractor.getFilterSettings()
            val updated = current.copy(region = null)
            filterSettingsInteractor.saveFilterSettings(updated)

            _uiState.update { it.copy(region = null) }
        }
    }

    /**
     * Кнопка "Выбрать" на этом экране ничего
     * дополнительно не сохраняет — страна/регион уже
     * лежат в FilterSettings (или очищены крестиком).
     */
    @Suppress("FunctionOnlyReturningConstant")
    suspend fun applySelection(): Boolean = true

    /**
     * Можно дергать после возврата с экранов выбора страны/региона.
     */
    fun refresh() {
        loadFromFilterSettings()
    }
}
