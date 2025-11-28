package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.FavoritesRepository

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override fun getFavorites(): Flow<List<VacancyDetails>> {
        return favoritesRepository.getFavorites()
    }

    override suspend fun addFavorite(vacancy: VacancyDetails) {
        return favoritesRepository.addFavorite(vacancy)
    }

    override suspend fun checkFavorite(vacancyId: String): Boolean {
        return favoritesRepository.checkFavorite(vacancyId)
    }

    override suspend fun deleteFavorite(vacancyId: String) {
        return favoritesRepository.deleteFavorite(vacancyId)
    }
}
