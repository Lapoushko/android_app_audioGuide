package com.lapoushko.domain.source

import com.lapoushko.domain.entity.Excursion
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface ExcursionDataSource{
    suspend fun deleteExcursion(excursion: Excursion)

    suspend fun saveExcursion(excursion: Excursion)

    fun getSavedExcursions(): Flow<List<Excursion>>
}