package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.state.FavoritesState

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state: StateFlow<FavoritesState> = _state

    /**
     * Явная загрузка избранных вакансий.
     * Вызывается из FavouritesScreen через LaunchedEffect(Unit).
     */
    fun loadFavorites() {
        viewModelScope.launch {
            favoritesInteractor.getFavorites()
                .catch { e ->
                    e.printStackTrace()
                    _state.value = FavoritesState.Error
                }
                .collect { vacancies ->
                    _state.value = if (vacancies.isEmpty()) {
                        FavoritesState.Empty
                    } else {
                        FavoritesState.Content(vacancies)
                    }
                }
        }
    }
}
