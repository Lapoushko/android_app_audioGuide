package com.lapoushko.data.repo

import com.lapoushko.domain.repo.CategoryRepository
import com.lapoushko.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
class CategoryRepositoryImpl(
    private val categoryService: CategoryService
) : CategoryRepository {
    override fun getCategories(): Flow<List<String>> {
        return categoryService.getCategories()
    }
}