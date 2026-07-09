package com.lapoushko.android_app_audioguide.di

import com.lapoushko.util.ConnectivityObserver
import com.lapoushko.util.NetworkConnectivityObserver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val utilModule = module{
    single<ConnectivityObserver> { NetworkConnectivityObserver(androidContext()) }
}