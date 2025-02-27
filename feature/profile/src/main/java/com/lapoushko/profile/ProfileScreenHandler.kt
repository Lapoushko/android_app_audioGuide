package com.lapoushko.profile

/**
 * @author Lapoushko
 */
class ProfileScreenHandler(
    private val onBack: () -> Unit,
    private val onSettings: () -> Unit,
    private val onSaves: () -> Unit,

) {
    fun onBack(){
        onBack.invoke()
    }

    fun onSettings(){
        onSettings.invoke()
    }

    fun onSaves(){
        onSaves.invoke()
    }
}