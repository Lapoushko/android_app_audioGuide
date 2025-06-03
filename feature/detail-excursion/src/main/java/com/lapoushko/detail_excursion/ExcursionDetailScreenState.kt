package com.lapoushko.detail_excursion

import com.lapoushko.feature.model.ExcursionItem
import com.lapoushko.util.ConnectivityObserver
import kotlinx.coroutines.Job

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

    val downloadJob: Job?

    val internetStatus: ConnectivityObserver.Status

    val isNeedToShowAuthDialog: Boolean
}

enum class DownloadAlertState {
    NONE,
    CONFIRM_SAVE,
    CONFIRM_DELETE,
    DOWNLOADING
}

data class DownloadValues(
    val startValue: Double,
    val endValue: Double?,
    val curValue: Double
)