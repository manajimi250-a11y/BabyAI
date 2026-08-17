package com.newlifetech.babyhey.audio

import android.content.Context
import android.media.MediaPlayer

/**
 * موزیک پس‌زمینه‌ی آروم برای داستان‌ها و بازی‌ها.
 * trackName باید اسم فایل توی res/raw باشه (بدون پسوند)؛ اگه نبود، بی‌خطر کاری نمی‌کنه.
 */
class MusicManager(private val context: Context) {
    private var player: MediaPlayer? = null
    private var isMuted = false

    fun start(trackName: String = "background_music") {
        if (player != null) return
        val resId = context.resources.getIdentifier(trackName, "raw", context.packageName)
        if (resId == 0) return
        try {
            player = MediaPlayer.create(context, resId)?.apply {
                isLooping = true
                setVolume(if (isMuted) 0f else 0.5f, if (isMuted) 0f else 0.5f)
                start()
            }
        } catch (_: Exception) {
        }
    }

    fun setMuted(muted: Boolean) {
        isMuted = muted
        player?.setVolume(if (muted) 0f else 0.5f, if (muted) 0f else 0.5f)
    }

    /** وقتی TTS/روایت داره صحبت می‌کنه، صدای موزیک رو کم می‌کنیم */
    fun duck() {
        if (!isMuted) player?.setVolume(0.12f, 0.12f)
    }

    fun restore() {
        if (!isMuted) player?.setVolume(0.5f, 0.5f)
    }

    fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}
