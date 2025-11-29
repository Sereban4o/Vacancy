package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoritesRepository {

    fun getFavorites(): Flow<List<VacancyDetails>>
    suspend fun addFavorite(vacancy: VacancyDetails)
    suspend fun deleteFavorite(vacancyId: String)
    suspend fun checkFavorite(vacancyId: String): Boolean
    suspend fun getFavoriteDetails(vacancyId: String): VacancyDetails?
}
