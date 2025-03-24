package com.lapoushko.network.mapper

import com.google.firebase.firestore.GeoPoint
import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.entity.Point
import com.lapoushko.network.entity.ExcursionNetwork

/**
 * @author Lapoushko
 */
interface ExcursionNetworkMapper {
    fun toDomain(excursion: ExcursionNetwork): Excursion

    fun toNetwork(excursion: Excursion): ExcursionNetwork
}

class ExcursionNetworkMapperImpl() : ExcursionNetworkMapper {
    override fun toDomain(excursion: ExcursionNetwork): Excursion {
        excursion.apply {
            return Excursion(
                id = id ?: "",
                name = name ?: "",
                description = description ?: "",
                categories = categories?.map { it } ?: emptyList(),
                distance = distance ?: 0,
                rating = rating ?: 0.0,
                countRating = countRating ?: 0,
                age = age ?: 0,
                points = points?.map {
                    Point(
                        name = it.name ?: "",
                        text = it.text ?: "",
                        image = it.image ?: "",
                        audio = it.audio ?: "",
                        point = Pair(it.point?.latitude ?: 0.0, it.point?.longitude ?: 0.0)
                    )
                } ?: emptyList()
            )
        }
    }

    override fun toNetwork(excursion: Excursion): ExcursionNetwork {
        excursion.apply {
            return ExcursionNetwork(
                id = id,
                name = name,
                description = description,
                categories = categories.map { it },
                distance = distance,
                rating = rating,
                age = age,
                countRating = countRating,
                points = points.map {
                    com.lapoushko.network.entity.Point(
                        name = it.name,
                        text = it.text,
                        image = it.image,
                        audio = it.audio,
                        point = GeoPoint(it.point.first, it.point.second)
                    )
                }

            )
        }
    }
}