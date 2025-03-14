package com.lapoushko.android_app_audioguide.di

import com.lapoushko.audio.screen.AudioScreenViewModel
import com.lapoushko.detail_excursion.ExcursionDetailScreenViewModel
import com.lapoushko.favourite.FavouriteScreenViewModel
import com.lapoushko.guide.GuideScreenViewModel
import com.lapoushko.map.MapScreenViewModel
import com.lapoushko.profile.ProfileScreenViewModel
import com.lapoushko.save.SaveExcursionScreenViewModel
import com.lapoushko.search.SearchScreenViewModel
import com.lapoushko.selection.CategoryScreenViewModel
import com.lapoushko.setting.SettingProfileScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val viewModelModule = module {
    viewModel { SearchScreenViewModel(get(), get(), get()) }
    viewModel { ExcursionDetailScreenViewModel(get(), get()) }
    viewModel { CategoryScreenViewModel(get(), get()) }
    viewModel { FavouriteScreenViewModel() }

    //profile
    viewModel { ProfileScreenViewModel() }
    viewModel { SaveExcursionScreenViewModel(get(), get()) }
    viewModel { SettingProfileScreenViewModel(get()) }
    //guide
    viewModel { AudioScreenViewModel(get()) }
    viewModel { MapScreenViewModel() }
    viewModel { GuideScreenViewModel() }
}