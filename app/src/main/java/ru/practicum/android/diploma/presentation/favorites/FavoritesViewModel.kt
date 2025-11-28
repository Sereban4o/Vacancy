package ru.practicum.android.diploma.presentation.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.state.FavoritesState

class FavoritesViewModel(
    application: Application,
    private val favoritesInteractor: FavoritesInteractor
) : AndroidViewModel(application) {

    private val stateLiveData = MutableLiveData<FavoritesState>()

    init {
        fillData()
    }

    fun observeState(): LiveData<FavoritesState> = stateLiveData

    fun fillData() {
        renderState(FavoritesState.Loading)
        viewModelScope.launch {
            favoritesInteractor.getFavorites().collect { vacancy ->
                processResult(vacancy)
            }
        }
    }

    private fun processResult(vacancy: List<VacancyDetails>) {
        if (vacancy.isEmpty()) {
            renderState(FavoritesState.Empty(true))
        } else {
            renderState(FavoritesState.Content(vacancy))
        }
    }

    private fun renderState(state: FavoritesState) {
        stateLiveData.postValue(state)
    }

}
