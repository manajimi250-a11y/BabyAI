package com.example.babyai.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "baby_ai_prefs")

/**
 * سایز نمایش عکس‌ها در صفحه بازی
 */
enum class PhotoSize { SMALL, MEDIUM, LARGE }

/**
 * منبع صدا برای یک کلمه: TTS دستگاه یا صدای ضبط‌شده والدین
 */
enum class VoiceSource { DEVICE_TTS, PARENT_RECORDING }

class UserPreferences(private val context: Context) {

    private object Keys {
        val LANGUAGE = stringPreferencesKey("language")           // "en" or "fa"
        val CHILD_AGE = intPreferencesKey("child_age")
        val CHILD_NAME = stringPreferencesKey("child_name")
        val MASCOT_ID = stringPreferencesKey("mascot_id")
        val PHOTO_SIZE = stringPreferencesKey("photo_size")
        val PARENTAL_GATE_ENABLED = booleanPreferencesKey("parental_gate_enabled")
        val TOTAL_STARS = intPreferencesKey("total_stars")
        val DISCOVERED_WORDS = stringPreferencesKey("discovered_words") // comma-separated word ids
        fun voiceSourceKey(wordId: String) = stringPreferencesKey("voice_source_$wordId")
    }

    val language: Flow<String> = context.dataStore.data.map { it[Keys.LANGUAGE] ?: "en" }
    val childAge: Flow<Int> = context.dataStore.data.map { it[Keys.CHILD_AGE] ?: 2 }
    val childName: Flow<String> = context.dataStore.data.map { it[Keys.CHILD_NAME] ?: "" }
    val totalStars: Flow<Int> = context.dataStore.data.map { it[Keys.TOTAL_STARS] ?: 0 }
    val discoveredWords: Flow<Set<String>> = context.dataStore.data.map {
        (it[Keys.DISCOVERED_WORDS] ?: "").split(",").filter { id -> id.isNotBlank() }.toSet()
    }

    /** اگه کلمه برای اولین‌بار کشف شده باشه، ۱ ستاره اضافه می‌کنه و true برمی‌گردونه */
    suspend fun markWordDiscovered(wordId: String): Boolean {
        var wasNew = false
        context.dataStore.edit { prefs ->
            val current = (prefs[Keys.DISCOVERED_WORDS] ?: "").split(",").filter { it.isNotBlank() }.toMutableSet()
            if (!current.contains(wordId)) {
                current.add(wordId)
                prefs[Keys.DISCOVERED_WORDS] = current.joinToString(",")
                prefs[Keys.TOTAL_STARS] = (prefs[Keys.TOTAL_STARS] ?: 0) + 1
                wasNew = true
            }
        }
        return wasNew
    }

    suspend fun addBonusStars(amount: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TOTAL_STARS] = (prefs[Keys.TOTAL_STARS] ?: 0) + amount
        }
    }
    val mascotId: Flow<String?> = context.dataStore.data.map { it[Keys.MASCOT_ID] }
    val photoSize: Flow<PhotoSize> = context.dataStore.data.map {
        PhotoSize.valueOf(it[Keys.PHOTO_SIZE] ?: PhotoSize.MEDIUM.name)
    }
    val parentalGateEnabled: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.PARENTAL_GATE_ENABLED] ?: false }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { it[Keys.LANGUAGE] = lang }
    }

    suspend fun setChildAge(age: Int) {
        context.dataStore.edit { it[Keys.CHILD_AGE] = age }
    }

    suspend fun setChildName(name: String) {
        context.dataStore.edit { it[Keys.CHILD_NAME] = name }
    }

    suspend fun setMascotId(id: String) {
        context.dataStore.edit { it[Keys.MASCOT_ID] = id }
    }

    suspend fun setPhotoSize(size: PhotoSize) {
        context.dataStore.edit { it[Keys.PHOTO_SIZE] = size.name }
    }

    suspend fun setParentalGateEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.PARENTAL_GATE_ENABLED] = enabled }
    }

    /** منبع صدا برای هر کلمه جدا ذخیره می‌شه (طبق تصمیم پروژه) */
    fun voiceSourceFor(wordId: String): Flow<VoiceSource> =
        context.dataStore.data.map {
            VoiceSource.valueOf(it[Keys.voiceSourceKey(wordId)] ?: VoiceSource.DEVICE_TTS.name)
        }

    suspend fun setVoiceSourceFor(wordId: String, source: VoiceSource) {
        context.dataStore.edit { it[Keys.voiceSourceKey(wordId)] = source.name }
    }
}
