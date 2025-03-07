package com.lapoushko.domain.usecase

import com.lapoushko.domain.repo.CategoryRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author Lapoushko
 */
interface SubscribeGetCategories {
    fun getCategories(): Flow<List<String>>
}

class SubscribeGetCategoriesImpl(
    private val repo: CategoryRepository
) : SubscribeGetCategories {
    override fun getCategories(): Flow<List<String>> {
        return repo.getCategories()
    }
}