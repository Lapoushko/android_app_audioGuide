package com.lapoushko.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lapoushko.storage.entity.ExcursionDb
import com.lapoushko.storage.util.ConstantsDatabase
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
@Dao
interface ExcursionDao{
    @Query("SELECT * FROM ${ConstantsDatabase.EXCURSION_TABLE_NAME}")
    fun getSavedExcursions(): Flow<List<ExcursionDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveExcursion(excursion: ExcursionDb)

    @Query("DELETE FROM ${ConstantsDatabase.EXCURSION_TABLE_NAME} WHERE id = :id")
    suspend fun deleteExcursion(id: String)
}
