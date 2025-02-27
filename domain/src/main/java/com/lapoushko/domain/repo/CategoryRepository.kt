package com.lapoushko.domain.repo

/**
 * @author Lapoushko
 */
interface CategoryRepository {
    suspend fun getCategories(): List<String>
}

class CategoryRepositoryImpl : CategoryRepository{
    override suspend fun getCategories(): List<String> {
        return List(5){"Категория"}
    }
}