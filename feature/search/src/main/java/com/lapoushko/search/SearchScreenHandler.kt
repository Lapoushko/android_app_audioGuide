package com.lapoushko.search

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
class SearchScreenHandler(
    private val onToCategory: (String) -> Unit,
    private val onToDetail: (ExcursionItem) -> Unit,
) {
    fun onToCategory(category: String) {
        onToCategory.invoke(category)
    }

    fun onToDetail(excursion: ExcursionItem){
        onToDetail.invoke(excursion)
    }
}