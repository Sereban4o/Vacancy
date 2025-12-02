package ru.practicum.android.diploma.presentation.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
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

    private fun fillData() {
        viewModelScope.launch {
            // Можно не ставить Loading тут, он уже стартовый,
            renderState(FavoritesState.Loading)

            favoritesInteractor.getFavorites()
                .catch { e ->
                    e.printStackTrace()
                    renderState(FavoritesState.Error)
                }
                .collect { vacancies ->
                    processResult(vacancies)
                }
        }
    }

    private fun processResult(vacancies: List<VacancyDetails>) {
        if (vacancies.isEmpty()) {
            renderState(FavoritesState.Empty)
        } else {
            renderState(FavoritesState.Content(vacancies))
        }
    }

    private fun renderState(state: FavoritesState) {
        _state.value = state
    }
}
