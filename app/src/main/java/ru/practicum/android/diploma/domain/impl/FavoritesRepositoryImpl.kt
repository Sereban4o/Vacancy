package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.convertor.VacancyDbConvertor
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repository.FavoritesRepository
import kotlin.collections.map

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConvertor: VacancyDbConvertor
) : FavoritesRepository {
    override fun getFavorites(): Flow<List<VacancyDetails>> {
        return appDatabase.vacancyDao().getAll().map { list: List<VacancyEntity> ->
            list.map { vacancy -> vacancyDbConvertor.map(vacancy) }
        }
    }

    override suspend fun addFavorite(vacancy: VacancyDetails) {
        appDatabase.vacancyDao().insert(vacancyDbConvertor.map(vacancy))
    }

    override suspend fun checkFavorite(vacancyId: String): Boolean {
        val result = appDatabase.vacancyDao().checkFavorite(vacancyId)
        return result.isNotEmpty()
    }

    override suspend fun deleteFavorite(vacancyId: String) {
        appDatabase.vacancyDao().delete(vacancyId)
    }

    override suspend fun getFavoriteDetails(vacancyId: String): VacancyDetails? {
        val entity = appDatabase.vacancyDao().getById(vacancyId)
        return entity?.let { vacancyDbConvertor.map(it) }
    }
}
