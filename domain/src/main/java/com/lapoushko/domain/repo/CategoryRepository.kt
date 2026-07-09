package com.lapoushko.domain.repo

import com.lapoushko.domain.entity.Category
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>
}