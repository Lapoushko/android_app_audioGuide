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
import org.koin.dsl.module

/**
 * @author Lapoushko
 */
val viewModelModule = module {
    single<SearchScreenViewModel> { SearchScreenViewModel(get(), get(), get()) }
    single<ExcursionDetailScreenViewModel> { ExcursionDetailScreenViewModel() }
    single<CategoryScreenViewModel> { CategoryScreenViewModel(get(), get()) }
    single<FavouriteScreenViewModel> { FavouriteScreenViewModel() }

    //profile
    single<ProfileScreenViewModel> { ProfileScreenViewModel() }
    single<SaveExcursionScreenViewModel> { SaveExcursionScreenViewModel(get(), get()) }
    single<SettingProfileScreenViewModel> { SettingProfileScreenViewModel(get()) }
    //guide
    single<AudioScreenViewModel> { AudioScreenViewModel(get()) }
    single<MapScreenViewModel> { MapScreenViewModel() }
    single<GuideScreenViewModel> { GuideScreenViewModel() }
}