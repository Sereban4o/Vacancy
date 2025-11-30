package ru.practicum.android.diploma.presentation.vacancydetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // достаём аргументы из SavedStateHandle
    private val vacancyId: String =
        checkNotNull(savedStateHandle[ARG_VACANCY_ID])

    private val fromApi: Boolean =
        savedStateHandle[ARG_FROM_API] ?: true

    private val _uiState = MutableStateFlow<VacancyDetailsUiState>(VacancyDetailsUiState.Loading)
    val uiState: StateFlow<VacancyDetailsUiState> = _uiState

    private val _events = MutableSharedFlow<VacancyDetailsEvent>()
    val events: SharedFlow<VacancyDetailsEvent> = _events

    init {
        loadDetails()
    }

    private fun loadDetails() {
        _uiState.value = VacancyDetailsUiState.Loading

        viewModelScope.launch {
            try {
                // 1️⃣ Выбираем источник данных
                val vacancy: VacancyDetails? = if (fromApi) {
                    // открыли из поиска → идём в API
                    interactor.getVacancyDetails(vacancyId) // не null
                } else {
                    // Открыли из избранного → берём из локальной БД
                    favoritesInteractor.getVacancyDetailsFromDb(vacancyId) // может быть null
                }

                // 2️⃣ Если из БД ничего не нашли → показываем NoVacancy
                if (vacancy == null) {
                    _uiState.value = VacancyDetailsUiState.NoVacancy
                } else {
                    // 3️⃣ Иначе — обычный успешный сценарий
                    val isFavorite = favoritesInteractor.checkFavorite(vacancyId)

                    _uiState.value = VacancyDetailsUiState.Content(
                        vacancy = vacancy,
                        isFavorite = isFavorite
                    )
                }

            } catch (e: IOException) {
                // 🔌 Нет интернета / проблемы с сетью (актуально при fromApi = true)
                _uiState.value = VacancyDetailsUiState.Error(isNetworkError = true)

            } catch (e: HttpException) {
                // 🌐 HTTP-ошибки (4xx/5xx)
                if (e.code() == HTTP_NOT_FOUND) {
                    // 🧩 Вакансия не найдена / удалена
                    _uiState.value = VacancyDetailsUiState.NoVacancy
                } else {
                    // Остальные HTTP-ошибки → общий серверный плейсхолдер
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
            _uiState.value = VacancyDetailsUiState.Content(
                vacancy, !isFavorite
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
