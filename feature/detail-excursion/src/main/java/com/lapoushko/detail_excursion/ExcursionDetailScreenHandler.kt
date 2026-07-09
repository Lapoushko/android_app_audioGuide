package com.lapoushko.detail_excursion

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
class ExcursionDetailScreenHandler(
    private val onBack: () -> Unit,
    private val onToDetail: (ExcursionItem) -> Unit,
    private val onPlayExcursion: (ExcursionItem) -> Unit
) {
    fun onBack() {
        onBack.invoke()
    }

    fun onToDetail(excursion: ExcursionItem) {
        onToDetail.invoke(excursion)
    }

    fun onPlayExcursion(excursion: ExcursionItem) {
        onPlayExcursion.invoke(excursion)
    }
}