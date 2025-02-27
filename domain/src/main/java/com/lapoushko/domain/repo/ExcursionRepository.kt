package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.Excursion

/**
 * @author Lapoushko
 */
interface ExcursionRepository {
    suspend fun getSavedExcursions(): List<Excursion>

    suspend fun getInterestingExcursions(): List<Excursion>

    suspend fun getPopularityExcursions(): List<Excursion>

    suspend fun getExcursionsByCategory(category: String): List<Excursion>

    suspend fun getExcursionByName(name: String): Excursion?
}

class ExcursionRepositoryImpl : ExcursionRepository{
    private val excursions = List(100) { index ->
        Excursion(
            id = index.toLong(),
            name = "Название $index",
            description = "Описание $index",
            category = "Категория",
            price = index * 100.0,
            distance = index * 50L,
            rating = index.toDouble(),
            countRating = index.toLong(),
        )
    }

    override suspend fun getSavedExcursions(): List<Excursion> {
        return excursions.take(10)
    }

    override suspend fun getInterestingExcursions(): List<Excursion> {
        return excursions.take(10)
    }

    override suspend fun getPopularityExcursions(): List<Excursion> {
        return excursions.take(10)
    }

    override suspend fun getExcursionsByCategory(category: String): List<Excursion> {
        return excursions.filter { it.category == category }.take(10)
    }

    override suspend fun getExcursionByName(name: String): Excursion? {
        return excursions.find { it.name == name }
    }
}