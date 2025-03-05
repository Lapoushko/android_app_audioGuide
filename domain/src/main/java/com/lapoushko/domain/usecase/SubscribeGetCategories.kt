package com.lapoushko.domain.usecase

import com.lapoushko.domain.repo.CategoryRepository

/**
 * @author Lapoushko
 */
interface SubscribeGetCategories {
    suspend fun getCategories(): List<String>
}

class SubscribeGetCategoriesImpl(
    private val repo: CategoryRepository
) : SubscribeGetCategories {
    override suspend fun getCategories(): List<String> {
        return repo.getCategories()
    }
}