package com.lapoushko.feature.mapper

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.domain.entity.Point
import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.feature.model.PointItem

/**
 * @author Lapoushko
 */
interface ExcursionMapper {
    fun toUi(excursion: Excursion): ExcursionItem

    fun toDomain(excursionItem: ExcursionItem): Excursion
}

class ExcursionMapperImpl : ExcursionMapper {
    override fun toUi(excursion: Excursion): ExcursionItem {
        excursion.apply {
            return ExcursionItem(
                id = id,
                name = name,
                description = description,
                categories = categories,
                distance = "$distance м.",
                age = "$age+",
                rating = rating,
                countRating = countRating,
                points = points.map {
                    PointItem(
                        name = it.name,
                        text = it.text,
                        image = it.image,
                        audio = it.audio,
                        point = it.point
                    )
                },
            )
        }
    }

    override fun toDomain(excursionItem: ExcursionItem): Excursion {
        excursionItem.apply {
            return Excursion(
                id = id,
                name = name,
                description = description,
                categories = categories,
                distance = distance.split(" ")[0].toLong(),
                rating = rating,
                countRating = countRating,
                age = age.replace("+","").toInt(),
                points = points.map {
                    Point(
                        name = it.name,
                        text = it.text,
                        image = it.image,
                        audio = it.audio,
                        point = it.point
                    )
                },
            )
        }
    }
}