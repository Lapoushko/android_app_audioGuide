package com.lapoushko.domain.service

import com.lapoushko.domain.entity.Excursion

/**
 * @author Lapoushko
 */
interface ExcursionService {
    suspend fun getSavedExcursions(): List<Excursion>

    suspend fun getInterestingExcursions(): List<Excursion>

    suspend fun getPopularityExcursions(): List<Excursion>

    suspend fun getExcursionsByCategory(category: String): List<Excursion>

    suspend fun getExcursionByName(name: String): Excursion?
}