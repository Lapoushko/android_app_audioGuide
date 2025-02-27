package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.repo.CategoryRepository
import com.lapoushko.domain.repo.CategoryRepositoryImpl
import com.lapoushko.domain.repo.ExcursionRepository
import com.lapoushko.domain.repo.ExcursionRepositoryImpl
import com.lapoushko.domain.repo.UserRepository
import com.lapoushko.domain.repo.UserRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val repositoryModule = module {
    singleOf(::ExcursionRepositoryImpl) { bind<ExcursionRepository>() }
    singleOf(::CategoryRepositoryImpl) { bind<CategoryRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}