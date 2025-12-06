package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.FilterParameter
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterViewModel(
    private val repository: FilterSettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterUiState())
    val uiState: StateFlow<FilterUiState> = _uiState

    private var currentSettings: FilterSettings = FilterSettings()

    /**
     * Явная загрузка настроек фильтра из репозитория.
     * Вызывается из FilterSettingsScreen через LaunchedEffect(Unit).
     */
    fun refreshFromRepository() {
        viewModelScope.launch {
            currentSettings = repository.getFilterSettings()
            _uiState.value = currentSettings.toUiState()
        }
    }

    // ----- ЗАРПЛАТА -----
    fun onSalaryChanged(text: String) {
        val digitsOnly = text.filter { it.isDigit() }
        _uiState.update { it.copy(salaryText = digitsOnly) }

        val value = digitsOnly.toIntOrNull()
        currentSettings = currentSettings.copy(salaryFrom = value)
        save()
    }

    fun clearSalary() {
        _uiState.update { it.copy(salaryText = "") }
        currentSettings = currentSettings.copy(salaryFrom = null)
        save()
    }

    // ----- ЧЕКБОКС -----
    fun toggleWithSalaryOnly(checked: Boolean) {
        _uiState.update { it.copy(withSalaryOnly = checked) }
        currentSettings = currentSettings.copy(withSalaryOnly = checked)
        save()
    }

    // ----- ОТРАСЛЬ -----
    fun setIndustry(industry: FilterParameter?) {
        _uiState.update { it.copy(industry = industry) }
        currentSettings = currentSettings.copy(industry = industry)
        save()
    }

    // ----- МЕСТО РАБОТЫ -----
    fun setCountry(country: FilterParameter?) {
        _uiState.update { it.copy(country = country) }
        currentSettings = currentSettings.copy(country = country)
        save()
    }

    fun setRegion(region: FilterParameter?) {
        _uiState.update { it.copy(region = region) }
        currentSettings = currentSettings.copy(region = region)
        save()
    }

    fun clearWorkPlace() {
        _uiState.update { it.copy(country = null, region = null) }
        currentSettings = currentSettings.copy(country = null, region = null)
        save()
    }

    fun resetAll() {
        viewModelScope.launch {
            repository.clearFilterSettings()
            currentSettings = FilterSettings()
            _uiState.value = currentSettings.toUiState()
        }
    }

    fun apply() {
        // no-op: настройки уже сохранены через save()
    }

    private fun save() = viewModelScope.launch {
        repository.saveFilterSettings(currentSettings)
    }
}
