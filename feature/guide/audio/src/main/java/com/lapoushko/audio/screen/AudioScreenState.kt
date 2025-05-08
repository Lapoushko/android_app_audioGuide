package com.lapoushko.audio.screen

import com.lapoushko.feature.model.ExcursionItem

/**
 * @author Lapoushko
 */
interface AudioScreenState {
    val excursion: ExcursionItem
    val isPlaying: Boolean
    val totalDurationInMS: Long
    val currentIndex: Int
    val currentPosition: Long
    val rate: Int
    val isNeedToShowRateDialog: Boolean
}