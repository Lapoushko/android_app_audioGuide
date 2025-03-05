package com.lapoushko.domain.usecase

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.repo.ExcursionRepository

/**
 * @author Lapoushko
 */
interface SubscribeGetExcursionUseCase {
    suspend fun getSavedExcursions(): List<Excursion>

    suspend fun getInterestingExcursions(): List<Excursion>

    suspend fun getPopularityExcursions(): List<Excursion>

    suspend fun getExcursionsByCategory(category: String): List<Excursion>

    suspend fun getExcursionByName(name: String): Excursion?
}

class SubscribeGetExcursionUseCaseImpl(
    private val repo: ExcursionRepository
) : SubscribeGetExcursionUseCase {
    override suspend fun getSavedExcursions(): List<Excursion> {
        return repo.getSavedExcursions()
    }

    override suspend fun getInterestingExcursions(): List<Excursion> {
        return repo.getInterestingExcursions()
    }

    override suspend fun getPopularityExcursions(): List<Excursion> {
        return repo.getPopularityExcursions()
    }

    override suspend fun getExcursionsByCategory(category: String): List<Excursion> {
        return repo.getExcursionsByCategory(category)
    }

    override suspend fun getExcursionByName(name: String): Excursion? {
        return repo.getExcursionByName(name)
    }
}