package com.example.babyai.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.audiofx.AcousticEchoCanceler
import android.media.audiofx.AutomaticGainControl
import android.media.audiofx.NoiseSuppressor
import java.io.File

/**
 * ضبط و پخش صدای والدین برای هر کلمه.
 * فایل‌ها جدا به ازای هر زبان ذخیره می‌شن: {wordId}_{lang}.m4a
 *
 * برای کیفیت بهتر: از AudioSource.MIC خام استفاده می‌کنیم (نه حالت مکالمه که
 * می‌تونه صدا رو خفه/پردازش‌شده کنه)، و به‌جاش noise suppression و
 * automatic gain control رو مستقیم و جدا روی سشن صدا فعال می‌کنیم.
 */
class RecordingManager(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var noiseSuppressor: NoiseSuppressor? = null
    private var agc: AutomaticGainControl? = null
    private var echoCanceler: AcousticEchoCanceler? = null

    private fun fileFor(wordId: String, lang: String): File =
        File(context.filesDir, "recordings/${wordId}_$lang.m4a").apply {
            parentFile?.mkdirs()
        }

    fun hasRecording(wordId: String, lang: String): Boolean =
        fileFor(wordId, lang).exists()

    fun startRecording(wordId: String, lang: String) {
        val file = fileFor(wordId, lang)
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioChannels(1)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(192000)
            setOutputFile(file.absolutePath)
            prepare()

            val sessionId = audioSessionId
            try {
                if (NoiseSuppressor.isAvailable()) {
                    noiseSuppressor = NoiseSuppressor.create(sessionId)?.apply { enabled = true }
                }
                if (AutomaticGainControl.isAvailable()) {
                    agc = AutomaticGainControl.create(sessionId)?.apply { enabled = true }
                }
                if (AcousticEchoCanceler.isAvailable()) {
                    echoCanceler = AcousticEchoCanceler.create(sessionId)?.apply { enabled = true }
                }
            } catch (_: Exception) {
                // اگه گوشی این افکت‌ها رو نداشت، بدون اونا هم ضبط ادامه پیدا می‌کنه
            }

            start()
        }
    }

    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        noiseSuppressor?.release()
        noiseSuppressor = null
        agc?.release()
        agc = null
        echoCanceler?.release()
        echoCanceler = null
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
