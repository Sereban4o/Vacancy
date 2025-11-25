package ru.practicum.android.diploma.presentation.vacancydetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.interactors.VacancyDetailsInteractor
import java.io.IOException

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val interactor: VacancyDetailsInteractor
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
                val vacancy = interactor.getVacancyDetails(vacancyId)

                Log.d(TAG, "УСПЕХ: получили VacancyDetails: $vacancy")

                _uiState.value = VacancyDetailsUiState.Content(vacancy)

            } catch (e: IOException) {
                // сетевые ошибки
                Log.e(TAG, "ОШИБКА СЕТИ: ${e.message}", e)
                _uiState.value = VacancyDetailsUiState.Error(isNetworkError = true)

            } catch (e: HttpException) {
                // HTTP-ошибки (4xx/5xx)
                Log.e(TAG, "ОШИБКА HTTP ${e.code()}: ${e.message()}", e)
                _uiState.value = VacancyDetailsUiState.Error(isNetworkError = false)
            }
        }
    }

    companion object {
        private const val TAG = "VacancyDetailsViewModel"
    }

}
