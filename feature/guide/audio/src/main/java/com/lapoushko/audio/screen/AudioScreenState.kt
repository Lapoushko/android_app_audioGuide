package com.lapoushko.audio.screen

/**
 * @author Lapoushko
 */
interface AudioScreenState {
    val isPlaying: Boolean
    val totalDurationInMS: Long
    val currentIndex: Int
}