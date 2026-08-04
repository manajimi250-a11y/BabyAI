package com.example.babyai.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * پخش‌کننده‌ی ساده برای لالایی‌های بی‌کلام (بخش «خواب‌های طلایی»).
 * هر بار فقط یه آهنگ پخش می‌شه و به‌صورت لوپ تکرار می‌شه؛
 * با شروع یه آهنگ جدید یا زدن استاپ، آهنگ قبلی خودکار متوقف می‌شه.
 */
class LullabyPlayer(private val context: Context) {
    private var player: MediaPlayer? = null

    var currentlyPlayingResId by mutableStateOf<Int?>(null)
        private set

    fun play(resId: Int) {
        stop()
        player = MediaPlayer.create(context, resId)
        player?.isLooping = true
        player?.start()
        currentlyPlayingResId = resId
    }

    fun stop() {
        player?.release()
        player = null
        currentlyPlayingResId = null
    }

    fun release() {
        stop()
    }
}
