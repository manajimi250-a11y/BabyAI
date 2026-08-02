package com.example.babyai.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * مدیریت پخش صدای واژه‌ها با Text-to-Speech دستگاه
 * با تنظیم pitch/rate برای صدای گرم‌تر و کمتر رباتیک
 */
class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false

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
        tts?.language = locale
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
