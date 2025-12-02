package ru.practicum.android.diploma.presentation.workplace

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

    init {
        loadFromFilterSettings()
    }

    private fun loadFromFilterSettings() {
        viewModelScope.launch {
            val settings: FilterSettings = filterSettingsInteractor.getFilterSettings()
            _uiState.value = WorkPlaceUiState(
                country = settings.country,
                region  = settings.region
            )
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
     * Просто говорим экрану "можно уходить назад".
     */
    suspend fun applySelection(): Boolean = true

    /**
     * Это пригодится позже, когда будут экраны выбора
     * страны/региона: после возврата можно дернуть refresh().
     */
    fun refresh() {
        loadFromFilterSettings()
    }
}
