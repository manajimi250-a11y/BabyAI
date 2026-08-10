package com.example.babyai.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File

/**
 * ضبط و پخش صدای والدین برای هر کلمه.
 * فایل‌ها جدا به ازای هر زبان ذخیره می‌شن: {wordId}_{lang}.m4a
 *
 * از AudioSource.VOICE_RECOGNITION استفاده می‌کنیم: روی اکثر گوشی‌ها این حالت
 * مخصوص گفتار واضحه و پردازش سرکوب نویز/تقویت صدا رو در سطح سخت‌افزار/درایور
 * انجام می‌ده، بدون این‌که مثل حالت مکالمه صدا رو خفه یا فشرده کنه.
 */
class RecordingManager(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    /** true اگه آخرین عملیات ضبط/پخش با خطا مواجه شده باشه */
    var lastError by mutableStateOf(false)
        private set

    private fun fileFor(wordId: String, lang: String): File =
        File(context.filesDir, "recordings/${wordId}_$lang.m4a").apply {
            parentFile?.mkdirs()
        }

    fun hasRecording(wordId: String, lang: String): Boolean =
        fileFor(wordId, lang).exists()

    fun startRecording(wordId: String, lang: String) {
        lastError = false
        val file = fileFor(wordId, lang)
        try {
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioChannels(1)
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(192000)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
        } catch (e: Exception) {
            lastError = true
            recorder?.release()
            recorder = null
        }
    }

    fun stopRecording() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            lastError = true
            recorder?.release()
        }
        recorder = null
    }

    /** پخش دقیقاً همون چیزی که والد ضبط کرده، بدون هیچ تغییری */
    fun play(wordId: String, lang: String) {
        lastError = false
        val file = fileFor(wordId, lang)
        if (!file.exists()) return
        player?.release()
        try {
            player = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                start()
            }
        } catch (e: Exception) {
            lastError = true
            player?.release()
            player = null
        }
    }

    fun deleteRecording(wordId: String, lang: String) {
        fileFor(wordId, lang).delete()
    }

    fun release() {
        player?.release()
        player = null
        try {
            recorder?.release()
        } catch (e: Exception) {
            // مشکلی نیست، داریم آزاد می‌کنیم
        }
        recorder = null
    }
}
