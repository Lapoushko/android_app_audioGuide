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
    override fun getSavedExcursions(): Flow<List<Excursion>> {
        return excursionDataSource.getSavedExcursions()
    }

    override suspend fun deleteExcursion(excursion: Excursion) {
        return excursionDataSource.deleteExcursion(excursion)
    }

    override suspend fun saveExcursion(
        excursion: Excursion,
        callBackFileDownloaded: (Double) -> Unit
    ): Excursion? {
        val newExcursion = excursionService.saveExcursion(excursion, callBackFileDownloaded)
        return newExcursion?.let {
            excursionDataSource.saveExcursion(newExcursion)
            newExcursion
        }
    }

    override suspend fun getSizeExcursion(excursion: Excursion): Double {
        return excursionService.getSize(excursion)
    }

    override fun getFavoritesExcursion(uid: String): Flow<List<Excursion>> {
        return excursionService.getFavouriteExcursions(uid)
    }

    override suspend fun saveFavouriteExcursion(excursion: Excursion, uid: String) {
        excursionService.saveFavouriteExcursion(excursion, uid)
    }

    override suspend fun deleteFavouriteExcursion(excursion: Excursion, uid: String) {
        excursionService.deleteFavouriteExcursion(excursion, uid)
    }

    override fun getInterestingExcursions(): Flow<List<Excursion>> {
        return excursionService.getInterestingExcursions()
    }

    override fun getPopularityExcursions(): Flow<List<Excursion>> {
        return excursionService.getPopularityExcursions()
    }

    override fun getExcursionsByCategory(category: String): Flow<List<Excursion>> {
        return excursionService.getExcursionsByCategory(category)
    }

    override fun getNewExcursions(): Flow<List<Excursion>> {
        return excursionService.getNewExcursions()
    }

    override fun getRecommendations(excursion: Excursion): Flow<List<Excursion>> {
        return excursionService.getRecommendation(excursion)
    }

    override suspend fun getSavedExcursion(id: String): Excursion {
        return excursionDataSource.getSavedExcursion(id)
    }
}