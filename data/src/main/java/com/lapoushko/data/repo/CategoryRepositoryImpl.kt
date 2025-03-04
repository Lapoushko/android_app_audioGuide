package com.lapoushko.data.repo

import com.lapoushko.domain.repo.CategoryRepository
import com.lapoushko.domain.service.CategoryService

/**
 * @author Lapoushko
 */
class CategoryRepositoryImpl(
    private val categoryService: CategoryService
) : CategoryRepository {
    override suspend fun getCategories(): List<String> {
        return categoryService.getCategories()
    }
}