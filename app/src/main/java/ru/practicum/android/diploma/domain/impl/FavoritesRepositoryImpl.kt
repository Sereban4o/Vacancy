package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.convertor.VacancyDbConvertor
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val vacancyDao: VacancyDao,
    private val vacancyDbConvertor: VacancyDbConvertor
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<VacancyDetails>> {
        return vacancyDao.getAll().map { list: List<VacancyEntity> ->
            list.map { entity -> vacancyDbConvertor.map(entity) }
        }
    }

    override suspend fun addFavorite(vacancy: VacancyDetails) {
        vacancyDao.insert(vacancyDbConvertor.map(vacancy))
    }

    override suspend fun checkFavorite(vacancyId: String): Boolean {
        return vacancyDao.checkFavorite(vacancyId).isNotEmpty()
    }

    override suspend fun deleteFavorite(vacancyId: String) {
        vacancyDao.delete(vacancyId)
    }

    override suspend fun getFavoriteDetails(vacancyId: String): VacancyDetails? {
        return vacancyDao.getById(vacancyId)?.let { vacancyDbConvertor.map(it) }
    }
}
