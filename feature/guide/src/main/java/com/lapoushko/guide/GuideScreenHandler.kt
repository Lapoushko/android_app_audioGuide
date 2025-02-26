package com.lapoushko.guide

import com.lapoushko.feature.model.ExcursionItem

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