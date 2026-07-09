package com.lapoushko.domain.service

import com.lapoushko.domain.entity.Category
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface CategoryService {
    fun getCategories(): Flow<List<Category>>
}