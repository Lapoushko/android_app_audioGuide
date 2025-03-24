package com.lapoushko.network.mapper

import com.lapoushko.domain.entity.Category
import com.lapoushko.network.entity.CategoryNetwork

/**
 * @author Lapoushko
 */
interface CategoryNetworkMapper{
    fun toDomain(categoryNetwork: CategoryNetwork): Category
}

class CategoryNetworkMapperImpl(): CategoryNetworkMapper{
    override fun toDomain(categoryNetwork: CategoryNetwork): Category {
        categoryNetwork.apply {
            return Category(
                name = name ?: "",
                image = image ?: ""
            )
        }
    }
}