package com.lapoushko.search

import com.lapoushko.feature.model.CategoryItem
import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
class SearchScreenHandler(
    private val onToCategory: (CategoryItem) -> Unit,
    private val onToDetail: (ExcursionItem) -> Unit,
) {
    fun onToCategory(category: CategoryItem) {
        onToCategory.invoke(category)
    }

    fun onToDetail(excursion: ExcursionItem) {
        onToDetail.invoke(excursion)
    }
}