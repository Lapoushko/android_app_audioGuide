package com.lapoushko.setting

import com.lapoushko.domain.entity.User
import com.lapoushko.domain.repo.UserRepository

/**
 * @author Lapoushko
 */
interface SettingProfileScreenState {
    val user: User?
    val response: UserRepository.AuthResponse?
    val isLoading: Boolean
    val dialogState: DialogState
    val isCorrectSignIn: Boolean

    enum class DialogState{
        EMPTY,
        AUTH,
        SING_OUT,
        DELETE
    }
}