package ru.practicum.android.diploma.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoritesInteractor {

    fun getFavorites(): Flow<List<VacancyDetails>>
    suspend fun addFavorite(vacancy: VacancyDetails)
    suspend fun checkFavorite(vacancyId: String): Boolean
    suspend fun deleteFavorite(vacancyId: String)
    suspend fun getVacancyDetailsFromDb(id: String): VacancyDetails?
}
