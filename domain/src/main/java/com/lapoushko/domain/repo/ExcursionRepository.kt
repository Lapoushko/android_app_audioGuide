package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.Excursion
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface ExcursionRepository {
    fun getSavedExcursions(): Flow<List<Excursion>>

    fun getInterestingExcursions(): Flow<List<Excursion>>

    fun getPopularityExcursions(): Flow<List<Excursion>>

    fun getExcursionsByCategory(category: String): Flow<List<Excursion>>

    fun getNewExcursions(): Flow<List<Excursion>>

    fun getRecommendations(excursion: Excursion): Flow<List<Excursion>>

    suspend fun deleteExcursion(excursion: Excursion)

    suspend fun saveExcursion(excursion: Excursion)

    suspend fun saveFavouriteExcursion(excursion: Excursion)

    suspend fun deleteFavouriteExcursion(excursion: Excursion)

    suspend fun getExcursionByName(name: String): Excursion?
}