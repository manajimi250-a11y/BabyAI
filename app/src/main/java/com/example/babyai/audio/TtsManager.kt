package com.example.babyai.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

/**
 * مدیریت پخش صدای واژه‌ها با Text-to-Speech دستگاه.
 *
 * نکته مهم درباره زمان‌بندی: راه‌اندازی موتور TTS ناهمگام (async) است — یعنی
 * بلافاصله بعد از ساختن TextToSpeech، موتور هنوز آماده نیست. برای همین
 * setLanguage ممکنه زودتر از موقع صدا زده بشه؛ این نسخه زبان درخواستی رو
 * نگه می‌داره (pending) و همین که موتور آماده شد، خودش اعمالش می‌کنه.
 */
class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private var pendingLanguageCode: String? = null

    var isCurrentLanguageSupported by mutableStateOf(true)
        private set

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (isReady) {
                tts?.setPitch(1.15f)
                tts?.setSpeechRate(0.85f)
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
        val availability = tts?.isLanguageAvailable(locale) ?: TextToSpeech.LANG_NOT_SUPPORTED
        isCurrentLanguageSupported = availability >= TextToSpeech.LANG_AVAILABLE
        // همیشه سعی می‌کنیم زبان رو تنظیم کنیم؛ فقط برای نمایش هشدار از availability استفاده می‌کنیم
        tts?.setLanguage(locale)
    }

    fun speak(text: String) {
        if (!isReady) return
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "baby_ai_utterance")
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
