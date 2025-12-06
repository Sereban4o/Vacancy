package ru.practicum.android.diploma.presentation.filter.country

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.interactors.CountriesInteractor
import ru.practicum.android.diploma.domain.interactors.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterParameter
import ru.practicum.android.diploma.domain.models.FilterSettings
import java.io.IOException

class CountryViewModel(
    private val countriesInteractor: CountriesInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryUiState(isLoading = true))
    val uiState: StateFlow<CountryUiState> = _uiState.asStateFlow()

    private var fullList: List<FilterParameter> = emptyList()

    // ---------------------------
    // 1. Основная функция (публичная)
    // ---------------------------
    fun loadCountries() {
        viewModelScope.launch {
            startLoading()

            try {
                val list = countriesInteractor.getCountries()
                fullList = sortSpecialCountriesToBottom(list)
                showContent(fullList)

            } catch (e: IOException) {
                handleNetworkError(e)

            } catch (e: HttpException) {
                handleHttpError(e)
            }
        }
    }

    // ---------------------------
    // ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ
    // ---------------------------

    private fun startLoading() {
        _uiState.update { it.copy(isLoading = true, isError = false) }
    }

    private fun sortSpecialCountriesToBottom(list: List<FilterParameter>): List<FilterParameter> {
        val special = setOf(
            "Другие регионы",
            "Другие страны",
            "Другая страна",
            "Прочее"
        )
        return list.sortedWith(compareBy { it.name in special })
    }

    private fun showContent(list: List<FilterParameter>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                isNetworkError = false,
                countries = list.toImmutableList()
            )
        }
    }

    private fun handleNetworkError(e: IOException) {
        Log.w(TAG, "Network error while loading countries", e)
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
                isNetworkError = true
            )
        }
    }

    private fun handleHttpError(e: HttpException) {
        Log.e(TAG, "HTTP error while loading countries: ${e.code()}", e)
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
                isNetworkError = false
            )
        }
    }

    // ---------------------------
    // Выбор страны
    // ---------------------------
    suspend fun selectCountry(countryId: String): Boolean {
        val selected = fullList.firstOrNull { it.id == countryId } ?: return false

        val current: FilterSettings = filterSettingsInteractor.getFilterSettings()
        val updated = current.copy(
            country = FilterParameter(selected.id, selected.name),
            region = null // 🔹 при смене страны регион сбрасываем
        )
        filterSettingsInteractor.saveFilterSettings(updated)
        return true
    }

    companion object {
        private const val TAG = "CountryViewModel"
    }
}
