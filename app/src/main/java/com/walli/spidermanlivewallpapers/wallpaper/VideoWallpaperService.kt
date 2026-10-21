package com.walli.spidermanlivewallpapers.wallpaper

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.walli.spidermanlivewallpapers.data.settings.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@AndroidEntryPoint
class VideoWallpaperService : WallpaperService() {

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }

    inner class VideoEngine : Engine() {
        private var exoPlayer: ExoPlayer? = null
        private val scope = CoroutineScope(Dispatchers.Main + Job())

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            initializePlayer(holder)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                exoPlayer?.play()
            } else {
                exoPlayer?.pause()
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            releasePlayer()
        }

        @OptIn(UnstableApi::class)
        private fun initializePlayer(holder: SurfaceHolder) {
            exoPlayer = ExoPlayer.Builder(this@VideoWallpaperService).build().apply {
                setVideoSurfaceHolder(holder)
                repeatMode = Player.REPEAT_MODE_ALL
                volume = 0f // Wallpapers should be silent
                
                addListener(object : Player.Listener {
                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        Log.e("VideoWallpaperService", "ExoPlayer error: ${error.message}", error)
                    }
                })

                scope.launch {
                    try {
                        val videoPath = settingsManager.liveWallpaperVideoPath.first()
                        if (!videoPath.isNullOrEmpty()) {
                            setMediaItem(MediaItem.fromUri(videoPath))
                            prepare()
                            if (isVisible) play()
                        }
                    } catch (e: Exception) {
                        Log.e("VideoWallpaperService", "Error loading video path", e)
                    }
                }
            }
        }

        private fun releasePlayer() {
            exoPlayer?.release()
            exoPlayer = null
        }

        override fun onDestroy() {
            super.onDestroy()
            releasePlayer()
        }
    }
}
