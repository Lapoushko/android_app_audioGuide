package com.lapoushko.android_app_audioguide

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.lapoushko.android_app_audioguide.di.daoModule
import com.lapoushko.android_app_audioguide.di.dataSourceModule
import com.lapoushko.android_app_audioguide.di.mapperModule
import com.lapoushko.android_app_audioguide.di.mediaModule
import com.lapoushko.android_app_audioguide.di.repositoryModule
import com.lapoushko.android_app_audioguide.di.serviceModule
import com.lapoushko.android_app_audioguide.di.utilModule
import com.lapoushko.android_app_audioguide.di.viewModelModule
import com.lapoushko.navigation.screen.BottomBarScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author Lapoushko
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                viewModelModule,
                repositoryModule,
                mapperModule,
                serviceModule,
                mediaModule,
                dataSourceModule,
                daoModule,
                utilModule
            )
        }
    }

    @Composable
    fun Host() {
        val navController = rememberNavController()
        BottomBarScreen(navController)
    }
}