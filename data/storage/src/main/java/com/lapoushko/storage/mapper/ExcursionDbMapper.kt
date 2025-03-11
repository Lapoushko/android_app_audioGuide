package com.lapoushko.storage.mapper

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.storage.entity.ExcursionDb
import com.lapoushko.storage.entity.Point

/**
 * @author Lapoushko
 */
interface ExcursionDbMapper {
    fun toDb(excursion: Excursion): ExcursionDb

    fun toDomain(excursionDb: ExcursionDb): Excursion
}

class ExcursionDbMapperImpl() : ExcursionDbMapper {
    override fun toDb(excursion: Excursion): ExcursionDb {
        excursion.apply {
            return ExcursionDb(
                id = id,
                name = name,
                description = description,
                distance = distance,
                categories = categories,
                countRating = countRating,
                price = price,
                rating = rating,
                points = points.map {
                    Point(
                        name = it.name,
                        text = it.text,
                        image = it.image,
                        point = it.point,
                        audio = it.audio
                    )
                }
            )
        }
    }

    override fun toDomain(excursionDb: ExcursionDb): Excursion {
        excursionDb.apply {
            return Excursion(
                id = id ?: "",
                name = name ?: "",
                description = description ?: "",
                categories = categories ?: emptyList(),
                price = price ?: 0.0,
                distance = distance ?: 0L,
                rating = rating ?: 0.0,
                countRating = countRating ?: 0,
                points = points?.map {
                    com.lapoushko.domain.entity.Point(
                        name = it.name ?: "",
                        text = it.text ?: "",
                        image = it.image ?: "",
                        point = it.point ?: Pair(0.0, 0.0),
                        audio = it.audio ?: ""
                    )
                } ?: emptyList(),
            )
        }
    }
}