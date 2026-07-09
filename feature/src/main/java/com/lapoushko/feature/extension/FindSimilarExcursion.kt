package com.lapoushko.feature.extension

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
fun String.searchByName(excursions: List<ExcursionItem>): List<ExcursionItem>{
    return excursions.filter {
        it.name.lowercase().contains(this.lowercase().trim())
    }
}