package ru.practicum.android.diploma.presentation.filter.industry

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.interactors.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.interactors.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.FilterParameter
import ru.practicum.android.diploma.domain.models.FilterSettings
import java.io.IOException

class IndustryViewModel(
    private val industriesInteractor: IndustriesInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(IndustryUiState(isLoading = true))
    val uiState: StateFlow<IndustryUiState> = _uiState.asStateFlow()

    // Полный список отраслей, без поиска
    private var fullList: List<FilterParameter> = emptyList()

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }

            try {
                // 1. Загружаем отрасли
                fullList = industriesInteractor.getIndustries()

                // 2. Читаем текущие настройки фильтра
                val currentSettings: FilterSettings = filterSettingsInteractor.getFilterSettings()
                val selectedId = currentSettings.industry?.id

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        industries = fullList, // пока без поиска
                        selectedIndustryId = selectedId
                    )
                }
            } catch (e: IOException) {
                // по ТЗ: показать сообщение об ошибке
                Log.w(TAG, "Failed to load industries (network error)", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            } catch (e: HttpException) {
                Log.w(TAG, "Failed to load industries (http error)", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        viewModelScope.launch {
            val trimmed = newQuery.trim()

            val filtered = if (trimmed.isEmpty()) {
                fullList
            } else {
                fullList.filter { industry ->
                    industry.name.contains(trimmed, ignoreCase = true)
                }
            }

            _uiState.update { state ->
                state.copy(
                    query = trimmed,
                    industries = filtered,
                    // сохраняем выбор, только если выбранная отрасль всё ещё в фильтрованном списке
                    selectedIndustryId = state.selectedIndustryId
                        ?.takeIf { id -> filtered.any { it.id == id } }
                )
            }
        }
    }

    fun onIndustryClick(industryId: String) {
        _uiState.update {
            it.copy(
                selectedIndustryId = industryId
            )
        }
    }

    /**
     * Сохраняем выбранную отрасль в FilterSettings.
     * Возвращаем true, если было что сохранять.
     */
    suspend fun applySelection(): Boolean {
        val state = _uiState.value
        val selectedId = state.selectedIndustryId

        // Находим выбранную отрасль, если id есть
        val selected = selectedId?.let { id ->
            fullList.firstOrNull { it.id == id }
        }

        // Если ничего не нашли — ничего не сохраняем
        if (selected == null) {
            return false
        }

        val current = filterSettingsInteractor.getFilterSettings()
        val updated = current.copy(
            industry = FilterParameter(
                id = selected.id,
                name = selected.name
            )
        )
        filterSettingsInteractor.saveFilterSettings(updated)

        // 🔹 очистить поле поиска и восстановить полный список
        _uiState.update {
            it.copy(
                query = "",
                industries = fullList
            )
        }

        return true
    }

    companion object {
        private const val TAG = "IndustryViewModel"
    }
}
