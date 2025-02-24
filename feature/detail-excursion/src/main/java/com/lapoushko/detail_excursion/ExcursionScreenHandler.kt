package com.lapoushko.detail_excursion

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
class ExcursionScreenHandler(
    private val onBack: () -> Unit,
    private val onToDetail: (ExcursionItem) -> Unit
) {
    fun onBack(){
        onBack.invoke()
    }

    fun onToDetail(excursion: ExcursionItem){
        onToDetail.invoke(excursion)
    }
}