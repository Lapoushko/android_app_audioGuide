package com.lapoushko.data.repo

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.domain.source.ExcursionDataSource
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
class ExcursionRepositoryImpl(
    private val excursionService: ExcursionService,
    private val excursionDataSource: ExcursionDataSource
) : ExcursionRepository {
    private val excursions = List(100) { index ->
        Excursion(
            id = "",
            name = "Название $index",
            description = "Описание $index",
            categories = listOf("Категория"),
            distance = index * 50L,
            rating = index.toDouble(),
            countRating = index.toLong(),
            points = emptyList(),
            age = 0
        )
    }

    override fun getSavedExcursions(): Flow<List<Excursion>> {
        return excursionDataSource.getSavedExcursions()
    }

    override suspend fun deleteExcursion(excursion: Excursion) {
        return excursionDataSource.deleteExcursion(excursion)
    }

    override suspend fun saveExcursion(excursion: Excursion) {
        excursionDataSource.saveExcursion(excursion)
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