package com.lapoushko.feature.mapper

import com.lapoushko.domain.entity.Excursion
import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
interface ExcursionMapper {
    fun toUi(excursion: Excursion): ExcursionItem

    fun toDomain(excursionItem: ExcursionItem) : Excursion
}

class ExcursionMapperImpl(): ExcursionMapper{
    override fun toUi(excursion: Excursion): ExcursionItem {
        excursion.apply {
            return ExcursionItem(
                id = id,
                name = name,
                description = description,
                category = categories,
                price = "$price р.",
                distance = "$distance м.",
                rating = rating,
                countRating = countRating,
                images = images,
                points = points
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
                price = price.split(" ")[0].toDouble(),
                distance = price.split(" ")[0].toLong(),
                rating = rating,
                countRating = countRating,
                images = images,
                points = points
            )
        }
    }
}