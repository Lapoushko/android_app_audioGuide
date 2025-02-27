package com.lapoushko.android_app_audioguide.di

import com.lapoushko.detail_excursion.ExcursionScreenViewModel
import com.lapoushko.search.SearchScreenViewModel
import com.lapoushko.setting.SettingProfileScreenViewModel
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val viewModelModule = module {
    single<SearchScreenViewModel>{SearchScreenViewModel()}
    single<ExcursionScreenViewModel>{ExcursionScreenViewModel()}
    single<SettingProfileScreenViewModel>{ SettingProfileScreenViewModel() }
}