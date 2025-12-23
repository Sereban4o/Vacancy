package ru.practicum.android.diploma.presentation.vacancydetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.interactors.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import java.io.IOException
import java.net.HttpURLConnection.HTTP_NOT_FOUND

class VacancyDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
) : ViewModel() {

    private val vacancyId: String =
        checkNotNull(savedStateHandle[ARG_VACANCY_ID])

    private val fromApi: Boolean =
        savedStateHandle[ARG_FROM_API] ?: true

    private val _uiState =
        MutableStateFlow<VacancyDetailsUiState>(VacancyDetailsUiState.Loading)
    val uiState: StateFlow<VacancyDetailsUiState> = _uiState

    private val _events = MutableSharedFlow<VacancyDetailsEvent>()
    val events: SharedFlow<VacancyDetailsEvent> = _events

    // 🚩 защита от повторных загрузок
    private var isLoaded = false

    /**
     * Вызываем из UI (LaunchedEffect), чтобы не триггерить сайд-эффекты в init.
     */
    fun loadDetailsIfNeeded() {
        if (isLoaded) return
        isLoaded = true
        loadDetailsInternal()
    }

    // опционально, если захочешь "обновить" по жесту pull-to-refresh:
    fun reload() {
        isLoaded = false
        loadDetailsIfNeeded()
    }

    private fun loadDetailsInternal() {
        _uiState.value = VacancyDetailsUiState.Loading

        viewModelScope.launch {
            try {
                val vacancy: VacancyDetails? = if (fromApi) {
                    interactor.getVacancyDetails(vacancyId)
                } else {
                    favoritesInteractor.getVacancyDetailsFromDb(vacancyId)
                }

                if (vacancy == null) {
                    _uiState.value = VacancyDetailsUiState.NoVacancy
                } else {
                    val isFavorite = favoritesInteractor.checkFavorite(vacancyId)

                    val descriptionItems =
                        parseVacancyDescription(vacancy.description).toImmutableList()

                    _uiState.value = VacancyDetailsUiState.Content(
                        vacancy = vacancy,
                        isFavorite = isFavorite,
                        descriptionItems = descriptionItems
                    )
                }

            } catch (e: IOException) {
                Log.e("VacancyDetailsViewModel", "Internet error: $e", e)
                _uiState.value = VacancyDetailsUiState.Error(isNetworkError = true)

            } catch (e: HttpException) {
                if (e.code() == HTTP_NOT_FOUND) {
                    _uiState.value = VacancyDetailsUiState.NoVacancy
                } else {
                    _uiState.value = VacancyDetailsUiState.Error(isNetworkError = false)
                }
            }
        }
    }

    fun editFavorite(vacancy: VacancyDetails, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                favoritesInteractor.deleteFavorite(vacancy.id)
            } else {
                favoritesInteractor.addFavorite(vacancy)
            }

            val descriptionItems =
                parseVacancyDescription(vacancy.description).toImmutableList()

            _uiState.value = VacancyDetailsUiState.Content(
                vacancy = vacancy,
                isFavorite = !isFavorite,
                descriptionItems = descriptionItems
            )
        }
    }

    fun onShareClick(url: String) {
        viewModelScope.launch {
            _events.emit(VacancyDetailsEvent.Share(url))
        }
    }

    fun onEmailClick(email: String) {
        viewModelScope.launch {
            _events.emit(VacancyDetailsEvent.Email(email))
        }
    }

    fun onPhoneClick(phone: String) {
        viewModelScope.launch {
            _events.emit(VacancyDetailsEvent.Call(phone))
        }
    }

    companion object {
        const val ARG_VACANCY_ID = "vacancyId"
        const val ARG_FROM_API = "fromApi"
    }
}
