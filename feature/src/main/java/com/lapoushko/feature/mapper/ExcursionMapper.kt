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
                category = categories,
                price = if (price == 0.0) "Бесплатно" else "$price р.",
                distance = "$distance м.",
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
                categories = category,
                price = if (price == "Бесплатно") 0.0 else price.split(" ")[0].toDouble(),
                distance = price.split(" ")[0].toLong(),
                rating = rating,
                countRating = countRating,
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