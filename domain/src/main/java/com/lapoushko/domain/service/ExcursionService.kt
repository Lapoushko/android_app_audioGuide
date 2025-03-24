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

    suspend fun getSavedExcursions(): List<Excursion>

    suspend fun getExcursionByName(name: String): Excursion?
}