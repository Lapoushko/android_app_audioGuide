package com.lapoushko.domain.repo

/**
 * @author Lapoushko
 */
interface CategoryRepository {
    suspend fun getCategories(): List<String>
}