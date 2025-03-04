package com.lapoushko.guide

/**
 * @author Lapoushko
 */
class GuideScreenHandler(
    private val onBack: () -> Unit
) {
    fun onBack() {
        onBack.invoke()
    }
}