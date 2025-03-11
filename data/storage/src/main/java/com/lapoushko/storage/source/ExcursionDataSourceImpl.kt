package com.lapoushko.storage.source

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.source.ExcursionDataSource
import com.lapoushko.storage.dao.ExcursionDao
import com.lapoushko.storage.mapper.ExcursionDbMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * @author Lapoushko
 */
class ExcursionDataSourceImpl(
    private val dao: ExcursionDao,
    private val mapper: ExcursionDbMapper
) : ExcursionDataSource {
    override suspend fun deleteExcursion(excursion: Excursion) {
        withContext(Dispatchers.IO){
            dao.deleteVacancy(excursion.id)
        }
    }

    override suspend fun saveExcursion(excursion: Excursion) {
        withContext(Dispatchers.IO){
            dao.saveExcursion(mapper.toDb(excursion))
        }
    }

    override fun getSavedExcursions(): Flow<List<Excursion>> =
        dao.getSavedExcursions()
            .map { list ->
                list.map { excursionDb ->
                    mapper.toDomain(excursionDb)
                }
            }
}