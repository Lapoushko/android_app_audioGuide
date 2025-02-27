package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.repo.CategoryRepository
import com.lapoushko.domain.repo.CategoryRepositoryImpl
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.domain.repo.ExcursionRepositoryImpl
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val repositoryModule = module {
    single<ExcursionRepository> { ExcursionRepositoryImpl() }
    single<CategoryRepository> { CategoryRepositoryImpl() }
}