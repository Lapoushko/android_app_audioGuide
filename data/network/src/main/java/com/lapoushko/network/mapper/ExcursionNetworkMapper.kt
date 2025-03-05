package com.lapoushko.network.mapper

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.entity.Point
import com.lapoushko.network.entity.ExcursionNetwork

/**
 * @author Lapoushko
 */
interface ExcursionNetworkMapper {
    fun toDomain(excursion: ExcursionNetwork): Excursion
}

class ExcursionNetworkMapperImpl() : ExcursionNetworkMapper {
    override fun toDomain(excursion: ExcursionNetwork): Excursion {
        excursion.apply {
            return Excursion(
                id = id ?: "",
                name = name ?: "",
                description = description ?: "",
                categories = categories?.map { it } ?: emptyList(),
                price = price ?: 0.0,
                distance = distance ?: 0,
                rating = rating ?: 0.0,
                countRating = countRating ?: 0,
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
}