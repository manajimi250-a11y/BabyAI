package com.example.babyai.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
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

    private fun fileFor(wordId: String, lang: String): File =
        File(context.filesDir, "recordings/${wordId}_$lang.m4a").apply {
            parentFile?.mkdirs()
        }

    fun hasRecording(wordId: String, lang: String): Boolean =
        fileFor(wordId, lang).exists()

    fun startRecording(wordId: String, lang: String) {
        val file = fileFor(wordId, lang)
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
    }

    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    /** پخش دقیقاً همون چیزی که والد ضبط کرده، بدون هیچ تغییری */
    fun play(wordId: String, lang: String) {
        val file = fileFor(wordId, lang)
        if (!file.exists()) return
        player?.release()
        player = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            prepare()
            start()
        }
    }

    fun deleteRecording(wordId: String, lang: String) {
        fileFor(wordId, lang).delete()
    }

    fun release() {
        player?.release()
        player = null
        recorder?.release()
        recorder = null
    }
}
