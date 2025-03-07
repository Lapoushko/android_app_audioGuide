package com.lapoushko.domain.service

import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface CategoryService {
    fun getCategories(): Flow<List<String>>
}