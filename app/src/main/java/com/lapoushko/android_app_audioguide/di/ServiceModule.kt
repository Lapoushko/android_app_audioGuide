package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.service.CategoryService
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.network.service.CategoryServiceImpl
import com.lapoushko.network.service.ExcursionServiceImpl
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val serviceModule = module {
    single<CategoryService>{CategoryServiceImpl()}
    single<ExcursionService> { ExcursionServiceImpl(get()) }
}