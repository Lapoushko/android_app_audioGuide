package com.lapoushko.domain.repo

import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface CategoryRepository {
    fun getCategories(): Flow<List<String>>
}