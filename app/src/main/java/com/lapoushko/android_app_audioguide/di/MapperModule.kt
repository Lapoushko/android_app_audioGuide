package com.lapoushko.android_app_audioguide.di

import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.mapper.ExcursionMapperImpl
import com.lapoushko.network.mapper.ExcursionNetworkMapper
import com.lapoushko.network.mapper.ExcursionNetworkMapperImpl
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val mapperModule = module {
    factory<ExcursionMapper> { ExcursionMapperImpl() }
    factory<ExcursionNetworkMapper> { ExcursionNetworkMapperImpl() }
}