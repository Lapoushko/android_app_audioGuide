package com.lapoushko.domain.service

/**
 * @author Lapoushko
 */
interface CategoryService {
    suspend fun getCategories(): List<String>
}