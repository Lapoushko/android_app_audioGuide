package com.lapoushko.android_app_audioguide.di

import androidx.media3.exoplayer.ExoPlayer
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val mediaModule = module {
    single<ExoPlayer> {
        ExoPlayer.Builder(androidApplication()).build()
    }
}
