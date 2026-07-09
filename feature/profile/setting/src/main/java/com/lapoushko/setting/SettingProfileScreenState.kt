package com.lapoushko.setting

import com.lapoushko.ui.model.Input

/**
 * @author Lapoushko
 */
interface SettingProfileScreenState {
    val name: Input
    val email: Input
    val firstPassword: Input
    val secondPassword: Input
}