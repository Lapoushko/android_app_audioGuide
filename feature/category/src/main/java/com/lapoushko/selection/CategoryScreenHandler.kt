package com.lapoushko.selection

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
class CategoryScreenHandler(
    private val onBack: () -> Unit,
    private val onToDetail: (ExcursionItem) -> Unit
) {
    fun onToDetail(excursion: ExcursionItem) {
        onToDetail.invoke(excursion)
    }

    fun onBack() {
        onBack.invoke()
    }
}