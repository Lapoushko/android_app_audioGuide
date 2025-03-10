package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.Excursion
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface ExcursionRepository {
    suspend fun getSavedExcursions(): List<Excursion>

    fun getInterestingExcursions(): Flow<List<Excursion>>

    suspend fun getPopularityExcursions(): List<Excursion>

    suspend fun getExcursionsByCategory(category: String): List<Excursion>

    suspend fun getExcursionByName(name: String): Excursion?
}