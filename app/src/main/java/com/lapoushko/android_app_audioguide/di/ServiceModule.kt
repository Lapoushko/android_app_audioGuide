package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.service.CategoryService
import com.lapoushko.domain.service.ExcursionService
import com.lapoushko.domain.service.UserService
import com.lapoushko.excursion.di.provideApiService
import com.lapoushko.excursion.di.provideRetrofit
import com.lapoushko.excursion.service.CategoryServiceImpl
import com.lapoushko.excursion.service.DownloadService
import com.lapoushko.excursion.service.ExcursionServiceImpl
import com.lapoushko.user.service.UserServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * @author Lapoushko
 */
val serviceModule = module {
    single<CategoryService> { CategoryServiceImpl(get()) }
    single<ExcursionService> {
        ExcursionServiceImpl(
            androidContext(),
            get(),
            get()
        )
    }

    single<Retrofit>{ provideRetrofit() }
    single<DownloadService> {
        provideApiService(
            get()
        )
    }

    single<UserService> { UserServiceImpl() }
}