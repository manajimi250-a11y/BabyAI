package com.example.babyai.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

/**
 * مدیریت پخش صدای واژه‌ها با Text-to-Speech دستگاه
 * با تنظیم pitch/rate برای صدای گرم‌تر و کمتر رباتیک
 *
 * نکته مهم: بسیاری از گوشی‌ها موتور TTS پیش‌فرض گوگل رو دارن که از
 * زبان فارسی پشتیبانی نمی‌کنه. برای همین isCurrentLanguageSupported رو
 * قبل از تکیه‌کردن به TTS چک کنید؛ اگه false بود، باید از
 * قابلیت «ضبط صدای والدین» به‌جای TTS استفاده بشه.
 */
class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    var isCurrentLanguageSupported by mutableStateOf(true)
        private set

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (isReady) {
                tts?.setPitch(1.15f)   // کمی زیرتر و گرم‌تر از صدای پیش‌فرض
                tts?.setSpeechRate(0.85f) // کمی آهسته‌تر برای وضوح بیشتر برای بچه‌ها
            }
        }
    }

    fun setLanguage(languageCode: String) {
        val locale = if (languageCode == "fa") Locale("fa", "IR") else Locale.US
        val result = tts?.setLanguage(locale) ?: TextToSpeech.LANG_NOT_SUPPORTED
        isCurrentLanguageSupported =
            result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
    }

    fun speak(text: String) {
        if (!isReady || !isCurrentLanguageSupported) return
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "baby_ai_utterance")
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
