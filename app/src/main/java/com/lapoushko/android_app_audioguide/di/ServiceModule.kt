package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.service.CategoryService
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.network.di.provideApiService
import com.lapoushko.network.di.provideRetrofit
import com.lapoushko.network.service.CategoryServiceImpl
import com.lapoushko.network.service.DownloadService
import com.lapoushko.network.service.ExcursionServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * @author Lapoushko
 */
val serviceModule = module {
    single<CategoryService> { CategoryServiceImpl(get()) }
    single<ExcursionService> { ExcursionServiceImpl(androidContext(), get(), get()) }

    single<Retrofit>{ provideRetrofit()}
    single<DownloadService> { provideApiService(get()) }
}