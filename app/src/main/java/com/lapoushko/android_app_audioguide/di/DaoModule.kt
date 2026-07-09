package com.lapoushko.android_app_audioguide.di

import com.lapoushko.storage.dao.ExcursionDao
import com.lapoushko.storage.dao.ExcursionDatabase
import com.lapoushko.storage.di.provideDao
import com.lapoushko.storage.di.provideRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val daoModule = module {
    single<ExcursionDatabase> { provideRoomDatabase(androidContext()) }
    single<ExcursionDao> { provideDao(get()) }
}
