package com.lapoushko.detail_excursion

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
interface ExcursionScreenState {
    val curExcursion: ExcursionItem
    val interestingExcursion: List<ExcursionItem>
}