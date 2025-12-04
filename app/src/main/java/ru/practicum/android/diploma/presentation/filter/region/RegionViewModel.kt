package ru.practicum.android.diploma.presentation.filter.region

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
import ru.practicum.android.diploma.domain.interactors.RegionsInteractor
import ru.practicum.android.diploma.domain.models.FilterParameter
import ru.practicum.android.diploma.domain.models.FilterSettings
import java.io.IOException

class RegionViewModel(
    private val regionsInteractor: RegionsInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegionUiState(isLoading = true))
    val uiState: StateFlow<RegionUiState> = _uiState.asStateFlow()

    // полный список регионов без поиска
    private var fullList: List<FilterParameter> = emptyList()

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            startLoading()

            try {
                val regions = fetchRegionsForCurrentFilter()
                onRegionsLoaded(regions)
            } catch (e: IOException) {
                Log.w(TAG, "Network error while loading regions", e)
                onRegionsLoadError()
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error while loading regions: ${e.code()}", e)
                onRegionsLoadError()
            }
        }
    }

    private fun startLoading() {
        _uiState.update {
            it.copy(
                isLoading = true,
                isError = false,
                isEmptySearchResult = false
            )
        }
    }

    private suspend fun fetchRegionsForCurrentFilter(): List<FilterParameter> {
        val settings: FilterSettings = filterSettingsInteractor.getFilterSettings()
        val currentCountryId = settings.country?.id
        return regionsInteractor.getRegionsForCountry(currentCountryId)
    }

    private fun onRegionsLoaded(regions: List<FilterParameter>) {
        fullList = regions
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                regions = fullList,
                isEmptySearchResult = false
            )
        }
    }

    private fun onRegionsLoadError() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
                isEmptySearchResult = false
            )
        }
    }

    fun onQueryChanged(newQuery: String) {
        viewModelScope.launch {
            val trimmed = newQuery.trim()

            _uiState.update { state ->
                if (trimmed.isEmpty()) {
                    // Пустой запрос → показываем полный список, без "Такого региона нет"
                    state.copy(
                        query = "",
                        regions = fullList,
                        isEmptySearchResult = false
                    )
                } else {
                    val filtered = fullList.filter { region ->
                        region.name.contains(trimmed, ignoreCase = true)
                    }

                    state.copy(
                        query = trimmed,
                        regions = filtered,
                        // ВАЖНО: пустой результат показываем только если НЕТ ошибки
                        isEmptySearchResult = trimmed.isNotBlank()
                            && filtered.isEmpty()
                            && !state.isError
                    )
                }
            }
        }
    }

    suspend fun selectRegion(regionId: String): Boolean {
        val selected = fullList.firstOrNull { it.id == regionId } ?: return false

        val current: FilterSettings = filterSettingsInteractor.getFilterSettings()

        val country: FilterParameter? = if (current.country != null) {
            current.country
        } else {
            regionsInteractor.getCountryForRegion(regionId)
        }

        val updated = current.copy(
            country = country ?: current.country,
            region = FilterParameter(
                id = selected.id,
                name = selected.name
            )
        )

        filterSettingsInteractor.saveFilterSettings(updated)
        return true
    }

    companion object {
        private const val TAG = "RegionViewModel"
    }
}
