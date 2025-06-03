package com.lapoushko.android_app_audioguide.di

import com.lapoushko.excursion.mapper.CategoryNetworkMapper
import com.lapoushko.excursion.mapper.CategoryNetworkMapperImpl
import com.lapoushko.excursion.mapper.ExcursionNetworkMapper
import com.lapoushko.excursion.mapper.ExcursionNetworkMapperImpl
import com.lapoushko.feature.mapper.ExcursionMapper
import com.lapoushko.feature.mapper.ExcursionMapperImpl
import com.lapoushko.storage.mapper.ExcursionDbMapper
import com.lapoushko.storage.mapper.ExcursionDbMapperImpl
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val mapperModule = module {
    factory<ExcursionMapper> { ExcursionMapperImpl() }
    factory<ExcursionNetworkMapper> { ExcursionNetworkMapperImpl() }
    factory<ExcursionDbMapper> { ExcursionDbMapperImpl() }
    factory<CategoryNetworkMapper> { CategoryNetworkMapperImpl() }
}