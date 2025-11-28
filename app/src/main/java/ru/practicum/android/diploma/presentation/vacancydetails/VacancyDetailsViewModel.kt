package ru.practicum.android.diploma.presentation.vacancydetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.interactors.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import java.io.IOException
import java.net.HttpURLConnection.HTTP_NOT_FOUND

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val interactor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val fromApi: Boolean, // 👈 новый флаг
) : ViewModel() {

    private val _uiState = MutableStateFlow<VacancyDetailsUiState>(VacancyDetailsUiState.Loading)
    val uiState: StateFlow<VacancyDetailsUiState> = _uiState

    init {
        loadDetails()
    }

    fun loadDetails() {
        _uiState.value = VacancyDetailsUiState.Loading

        viewModelScope.launch {
            Log.d(TAG, "Запрашиваем детали вакансии через interactor, id=$vacancyId")

            try {
                // 1️⃣ Выбираем источник данных
                val vacancy: VacancyDetails = if (fromApi) {
                    // открыли из поиска → идём в API
                    interactor.getVacancyDetails(vacancyId)
                } else {
                    // Открыли из избранного → берём из локальной БД
                    val fromDb = favoritesInteractor.getVacancyDetailsFromDb(vacancyId)

                    if (fromDb == null) {
                        // В БД ничего не нашли → показываем плейсхолдер "вакансии нет"
                        _uiState.value = VacancyDetailsUiState.NoVacancy
                        return@launch
                    }

                    fromDb
                }

                // 2️⃣ Проверяем, в избранном ли эта вакансия
                val isFavorite = favoritesInteractor.checkFavorite(vacancyId)

                Log.d(TAG, "УСПЕХ: получили VacancyDetails: $vacancy")

                // 3️⃣ Отдаём контент в UI
                _uiState.value = VacancyDetailsUiState.Content(
                    vacancy = vacancy,
                    isFavorite = isFavorite
                )

            } catch (e: IOException) {
                // 🔌 Нет интернета / проблемы с сетью (актуально при fromApi = true)
                Log.e(TAG, "ОШИБКА СЕТИ: ${e.message}", e)
                _uiState.value = VacancyDetailsUiState.Error(isNetworkError = true)

            } catch (e: HttpException) {
                // 🌐 HTTP-ошибки (4xx/5xx)
                Log.e(TAG, "ОШИБКА HTTP ${e.code()}: ${e.message()}", e)

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

    companion object {
        private const val TAG = "VacancyDetailsViewModel"
    }
}
