package com.lapoushko.audio.screen

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.lapoushko.audio.manager.MediaNotificationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @author Lapoushko
 */
@UnstableApi
class AudioScreenViewModel(
    private val player: ExoPlayer
) : ViewModel() {
    private var _state = MutableAudioScreenState()
    val state = _state as AudioScreenState

    private lateinit var notificationManager: MediaNotificationManager

    private lateinit var mediaSession: MediaSession

    private var isStarted = false

    fun preparePlayer(context: Context, playlist: List<PlaylistItem>) {
        viewModelScope.launch(Dispatchers.Main) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                .build()

            player.setAudioAttributes(audioAttributes, true)
            player.repeatMode = Player.REPEAT_MODE_ALL

            player.addListener(playerListener)

            setupPlaylist(context, playlist)
        }
    }

    private fun setupPlaylist(context: Context, playlist: List<PlaylistItem>) {

        val videoItems: ArrayList<MediaSource> = arrayListOf()
        playlist.forEach {

            val mediaMetaData = MediaMetadata.Builder()
                .setTitle(it.title)
                .build()

            val trackUri = Uri.parse(it.audio)
            val mediaItem = MediaItem.Builder()
                .setUri(trackUri)
                .setMediaMetadata(mediaMetaData)
                .build()
            val dataSourceFactory = DefaultDataSource.Factory(context)

            val mediaSource =
                ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

            videoItems.add(
                mediaSource
            )
        }

        onStart(context)

        player.playWhenReady = false
        player.setMediaSources(videoItems)
        player.prepare()
    }

    fun updatePlaylist(action: ControlButtons) {
        when (action) {
            ControlButtons.PLAY -> if (player.isPlaying) player.pause() else player.play()
            ControlButtons.NEXT -> player.seekToNextMediaItem()
            ControlButtons.PREVIOUS -> player.seekToPreviousMediaItem()
        }
    }

    fun updatePlayerPosition(position: Long) {
        player.seekTo(position)
    }

    fun onStart(context: Context) {
        if (isStarted) return
        isStarted = true
        val sessionActivityPendingIntent =
            context.packageManager?.getLaunchIntentForPackage(context.packageName)
                ?.let { sessionIntent ->
                    PendingIntent.getActivity(
                        context,
                        1001,
                        sessionIntent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                }

        // Create a new MediaSession.
        mediaSession = MediaSession.Builder(context, player)
            .setSessionActivity(sessionActivityPendingIntent!!).build()

        notificationManager =
            MediaNotificationManager(
                context,
                mediaSession.token,
                player,
                PlayerNotificationListener()
            )


        notificationManager.showNotificationForPlayer(player)
    }

    /**
     * Destroy audio notification
     */
    fun onDestroy() {
        onClose()
        player.release()
    }

    /**
     * Close audio notification
     */
    fun onClose() {
        if (!isStarted) return

        isStarted = false
        mediaSession.run {
            release()
        }

        // Hide notification
        notificationManager.hideNotification()

        // Free ExoPlayer resources.
        player.removeListener(playerListener)
    }

    /**
     * Listen for notification events.
     */
    private inner class PlayerNotificationListener :
        PlayerNotificationManager.NotificationListener {
        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {

        }

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {

        }
    }

    /**
     * Listen to events from ExoPlayer.
     */
    private val playerListener = object : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            syncPlayerFlows()
            when (playbackState) {
                Player.STATE_BUFFERING,
                Player.STATE_READY -> {
                    notificationManager.showNotificationForPlayer(player)
                }

                else -> {
                    notificationManager.hideNotification()
                }
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            syncPlayerFlows()
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            _state.isPlaying = isPlaying
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Log.e("Errors", "Error: ${error.message}")
        }
    }

    private fun syncPlayerFlows() {
        _state.currentIndex = player.currentMediaItemIndex
        _state.totalDurationInMS = player.duration.coerceAtLeast(0L)
    }

    private class MutableAudioScreenState() : AudioScreenState {
        override var isPlaying: Boolean by mutableStateOf(false)
        override var totalDurationInMS: Long by mutableLongStateOf(0L)
        override var currentIndex: Int by mutableIntStateOf(0)
    }
}

class PlaylistItem(
    val title: String,
    val audio: String
)

enum class ControlButtons {
    PLAY,
    NEXT,
    PREVIOUS
}
