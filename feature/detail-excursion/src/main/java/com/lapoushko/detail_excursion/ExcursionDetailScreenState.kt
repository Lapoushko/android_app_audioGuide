package com.lapoushko.detail_excursion

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
interface ExcursionDetailScreenState {
    val curExcursion: ExcursionItem
    val interestingExcursion: List<ExcursionItem>
    val downloadAlertState: DownloadAlertState

    val isSaved: Boolean
    val isFavourite: Boolean
    val isSaveButtonActive: Boolean

    val downloadValues: DownloadValues
}

enum class DownloadAlertState(){
    DELETING,
    SAVING,
    DOWNLOADING,
    EMPTY
}

data class DownloadValues(
    val startValue: Double,
    val endValue: Double,
    val curValue: Double
)