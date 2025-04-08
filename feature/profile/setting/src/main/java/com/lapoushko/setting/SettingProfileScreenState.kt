package com.lapoushko.setting

import com.lapoushko.domain.repo.UserRepository

/**
 * @author Lapoushko
 */
interface SettingProfileScreenState {
    val response: UserRepository.AuthResponse?
    val isLoading: Boolean
    val isNeedToShowAuthDialog: Boolean
    val isCorrectSignIn: Boolean
}