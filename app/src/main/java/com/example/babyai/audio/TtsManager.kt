package com.example.babyai.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

/**
 * مدیریت پخش صدای واژه‌ها با Text-to-Speech دستگاه.
 *
 * به‌جای حدس‌زدن از قبل که آیا زبان پشتیبانی می‌شه یا نه (که غیرقابل‌اعتماد بود)،
 * این نسخه واقعاً speak رو امتحان می‌کنه و از طریق UtteranceProgressListener
 * می‌فهمه که آیا واقعاً صدایی پخش شد یا با خطا مواجه شد.
 */
class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private var pendingLanguageCode: String? = null

    /** هر بار که یه speak با خطا مواجه بشه (یعنی احتمالاً زبان پشتیبانی نمی‌شه)، true می‌شه */
    var lastSpeakFailed by mutableStateOf(false)
        private set

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (isReady) {
                tts?.setPitch(1.15f)
                tts?.setSpeechRate(0.85f)
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        lastSpeakFailed = false
                    }
                    override fun onDone(utteranceId: String?) {}
                    override fun onError(utteranceId: String?) {
                        lastSpeakFailed = true
                    }
                    override fun onError(utteranceId: String?, errorCode: Int) {
                        lastSpeakFailed = true
                    }
                })
                pendingLanguageCode?.let { applyLanguage(it) }
            }
        }
    }

    fun setLanguage(languageCode: String) {
        pendingLanguageCode = languageCode
        if (isReady) {
            applyLanguage(languageCode)
        }
    }

    private fun applyLanguage(languageCode: String) {
        val locale = if (languageCode == "fa") Locale("fa", "IR") else Locale.ENGLISH
        tts?.setLanguage(locale)
    }

    fun speak(text: String) {
        if (!isReady) {
            lastSpeakFailed = true
            return
        }
        val result = tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "baby_ai_utterance")
        if (result != TextToSpeech.SUCCESS) {
            lastSpeakFailed = true
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
