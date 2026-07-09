package com.lapoushko.profile

/**
 * @author Lapoushko
 */
class ProfileScreenHandler(
    private val onSettings: () -> Unit,
    private val onSaves: () -> Unit,

    ) {
    fun onSettings() {
        onSettings.invoke()
    }

    fun onSaves() {
        onSaves.invoke()
    }
}