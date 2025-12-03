package ru.practicum.android.diploma.presentation.filter.country

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // весь список, если потом понадобится фильтрация
    private var fullList: List<FilterParameter> = emptyList()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            try {
                fullList = countriesInteractor.getCountries()

                // 🔥 Добавляем Андрей список спец-названий
                val specialNames = setOf(
                    "Другие регионы",
                    "Другие страны",
                    "Другая страна",
                    "Прочее"
                )

                // 🔥 Переносим такие элементы в самый низ
                fullList = fullList.sortedWith(
                    compareBy { it.name in specialNames }
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        isNetworkError = false,
                        countries = fullList
                    )
                }
            } catch (e: IOException) {
                // нет интернета
                Log.w(TAG, "Network error while loading countries", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        isNetworkError = true
                    )
                }
            } catch (e: HttpException) {
                // ошибка HTTP (4xx / 5xx)
                Log.e(TAG, "HTTP error while loading countries: ${e.code()}", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        isNetworkError = false
                    )
                }
            }
        }
    }

    /**
     * Пользователь выбрал страну: сохраняем её в FilterSettings
     * и сбрасываем регион (по логике — старый регион может не подходить).
     */
    suspend fun selectCountry(countryId: String): Boolean {
        val selected = fullList.firstOrNull { it.id == countryId } ?: return false

        val current: FilterSettings = filterSettingsInteractor.getFilterSettings()
        val updated = current.copy(
            country = FilterParameter(
                id = selected.id,
                name = selected.name
            ),
            region = null // 🔹 при смене страны регион сбрасываем
        )
        filterSettingsInteractor.saveFilterSettings(updated)
        return true
    }

    companion object {
        private const val TAG = "CountryViewModel"
    }
}
