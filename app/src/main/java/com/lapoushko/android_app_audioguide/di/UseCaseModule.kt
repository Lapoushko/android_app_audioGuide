package com.lapoushko.android_app_audioguide.di

import com.lapoushko.domain.usecase.SubscribeGetCategories
import com.lapoushko.domain.usecase.SubscribeGetCategoriesImpl
import com.lapoushko.domain.usecase.SubscribeGetExcursionUseCase
import com.lapoushko.domain.usecase.SubscribeGetExcursionUseCaseImpl
import com.lapoushko.domain.usecase.SubscribeGetUser
import com.lapoushko.domain.usecase.SubscribeGetUserImpl
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val useCaseModule = module {
    single<SubscribeGetExcursionUseCase> { SubscribeGetExcursionUseCaseImpl(get()) }
    single<SubscribeGetCategories> { SubscribeGetCategoriesImpl(get()) }
    single<SubscribeGetUser> { SubscribeGetUserImpl(get()) }
}