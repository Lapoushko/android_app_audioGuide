package com.lapoushko.data.repo

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.domain.service.ExcursionService
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
class ExcursionRepositoryImpl(
    private val excursionService: ExcursionService
) : ExcursionRepository {
    private val excursions = List(100) { index ->
        Excursion(
            id = "",
            name = "Название $index",
            description = "Описание $index",
            categories = listOf("Категория"),
            price = index * 100.0,
            distance = index * 50L,
            rating = index.toDouble(),
            countRating = index.toLong(),
            points = emptyList(),
        )
    }

    override suspend fun getSavedExcursions(): List<Excursion> {
        return excursions.take(10)
    }

    override fun getInterestingExcursions(): Flow<List<Excursion>> {
        return excursionService.getInterestingExcursions()
    }

    override suspend fun getPopularityExcursions(): List<Excursion> {
        return excursions.take(10)
    }

    override suspend fun getExcursionsByCategory(category: String): List<Excursion> {
        return excursions.filter { it.categories.contains(category) }.take(10)
    }

    override suspend fun getExcursionByName(name: String): Excursion? {
        return excursions.find { it.name == name }
    }
}