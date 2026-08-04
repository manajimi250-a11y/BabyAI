package com.example.babyai.audio

import android.content.Context
import android.media.MediaPlayer
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
 *
 * برای فارسی: قبل از تکیه به TTS، اول سراغ فایل‌های صوتی از پیش ضبط‌شده
 * (res/raw، اسمشون مثل animals_dog) می‌ریم که همیشه درست کار کنن.
 */
class TtsManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private var pendingLanguageCode: String? = null
    private var bundledPlayer: MediaPlayer? = null

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
        val locale = when (languageCode) {
            "fa" -> Locale("fa", "IR")
            "sv" -> Locale("sv", "SE")
            "tr" -> Locale("tr", "TR")
            "de" -> Locale.GERMAN
            "fr" -> Locale.FRENCH
            "es" -> Locale("es", "ES")
            "ru" -> Locale("ru", "RU")
            "zh" -> Locale.SIMPLIFIED_CHINESE
            "hi" -> Locale("hi", "IN")
            "ar" -> Locale("ar", "SA")
            else -> Locale.ENGLISH
        }
        tts?.setLanguage(locale)
    }

    /**
     * سعی می‌کنه فایل صوتی از پیش‌ضبط‌شده رو پخش کنه (مثلاً "animals_dog").
     * اگه پیدا شد و پخش شد، true برمی‌گردونه؛ اگه نبود، false (باید از TTS استفاده کنیم).
     */
    var lastBundledError by mutableStateOf<String?>(null)
        private set

    fun playBundledAudio(resourceName: String): Boolean {
        val resId = context.resources.getIdentifier(resourceName, "raw", context.packageName)
        if (resId == 0) {
            lastBundledError = "resource not found: $resourceName"
            return false
        }
        return try {
            bundledPlayer?.release()
            bundledPlayer = MediaPlayer.create(context, resId)
            if (bundledPlayer == null) {
                lastBundledError = "MediaPlayer.create returned null for $resourceName"
                return false
            }
            bundledPlayer?.start()
            lastBundledError = null
            true
        } catch (e: Exception) {
            lastBundledError = "${e.javaClass.simpleName}: ${e.message}"
            false
        }
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
        bundledPlayer?.release()
        bundledPlayer = null
    }
}
