package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VacancyDao {

    @Query("SELECT * FROM vacancies ORDER BY id DESC")
    fun getAll(): Flow<List<VacancyEntity>>

    @Insert(entity = VacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: VacancyEntity)

    @Query("SELECT * FROM vacancies WHERE Id = :vacancyId")
    suspend fun checkFavorite(vacancyId: String): List<VacancyEntity>

    @Query("DELETE FROM vacancies WHERE Id = :vacancyId")
    suspend fun delete(vacancyId: String)
}
