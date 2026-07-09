package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.source.ExcursionDataSource
import com.lapoushko.storage.source.ExcursionDataSourceImpl
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val dataSourceModule = module {
    single<ExcursionDataSource> { ExcursionDataSourceImpl(get(), get()) }
}