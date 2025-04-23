package com.lapoushko.android_app_audioguide.di

import com.lapoushko.audio.screen.AudioScreenViewModel
import com.lapoushko.detail_excursion.ExcursionDetailScreenViewModel
import com.lapoushko.favourite.FavouriteScreenViewModel
import com.lapoushko.feature.auth.AuthHelperViewModel
import com.lapoushko.guide.GuideScreenViewModel
import com.lapoushko.map.MapScreenViewModel
import com.lapoushko.profile.ProfileScreenViewModel
import com.lapoushko.save.SaveExcursionScreenViewModel
import com.lapoushko.search.SearchScreenViewModel
import com.lapoushko.selection.CategoryScreenViewModel
import com.lapoushko.setting.SettingProfileScreenViewModel
import com.lapoushko.ui.auth.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val viewModelModule = module {
    //screen
    viewModel { SearchScreenViewModel(get(), get(), get(), get()) }
    viewModel { ExcursionDetailScreenViewModel(get(), get(), get()) }
    viewModel { CategoryScreenViewModel(get(), get()) }
    viewModel { FavouriteScreenViewModel(get(), get(), get()) }

    //profile
    viewModel { ProfileScreenViewModel() }
    viewModel { SaveExcursionScreenViewModel(get(), get()) }
    viewModel { SettingProfileScreenViewModel(get()) }
    //guide
    viewModel { AudioScreenViewModel(get(),get()) }
    viewModel { MapScreenViewModel() }
    viewModel { GuideScreenViewModel(get()) }

    //common
    viewModel { AuthViewModel() }

    //helper
    viewModel { AuthHelperViewModel(get()) }
}