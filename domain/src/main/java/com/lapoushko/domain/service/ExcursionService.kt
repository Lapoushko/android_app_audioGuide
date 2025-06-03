package com.lapoushko.domain.service

import com.lapoushko.domain.entity.Excursion
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface ExcursionService {
    fun getInterestingExcursions(): Flow<List<Excursion>>

    fun getPopularityExcursions(): Flow<List<Excursion>>

    fun getExcursionsByCategory(category: String): Flow<List<Excursion>>

    fun getNewExcursions(): Flow<List<Excursion>>

    fun getRecommendation(excursion: Excursion): Flow<List<Excursion>>

    fun getFavouriteExcursions(uid: String): Flow<List<Excursion>>

    suspend fun saveFavouriteExcursion(excursion: Excursion, uid: String)

    suspend fun deleteFavouriteExcursion(excursion: Excursion, uid: String)

    suspend fun getSize(excursion: Excursion): Double

    suspend fun saveExcursion(excursion: Excursion, callBackFileDownloaded: (Double) -> Unit): Excursion?
}

enum class TypeFile(val naming: String) {
    IMAGE("image"),
    AUDIO("audio")
}