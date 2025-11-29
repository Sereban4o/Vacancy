package ru.practicum.android.diploma.presentation.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.state.FavoritesState

class FavoritesViewModel(
    application: Application,
    private val favoritesInteractor: FavoritesInteractor
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state: StateFlow<FavoritesState> = _state

    init {
        fillData()
    }

    fun fillData() {
        renderState(FavoritesState.Loading)
        viewModelScope.launch {
            try {
                favoritesInteractor.getFavorites().collect { vacancies ->
                    processResult(vacancies)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                renderState(FavoritesState.Error)
            }
        }
    }

    private fun processResult(vacancy: List<VacancyDetails>) {
        if (vacancy.isEmpty()) {
            renderState(FavoritesState.Empty)
        } else {
            renderState(FavoritesState.Content(vacancy))
        }
    }

    private fun renderState(state: FavoritesState) {
        _state.value = state
    }
}
