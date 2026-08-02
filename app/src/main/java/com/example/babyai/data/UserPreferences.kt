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

/**
 * یک پروفایل بازیکن (بچه) با پیشرفت جدا
 */
data class Profile(
    val id: String,
    val name: String,
    val age: Int,
    val mascotId: String,
    val stars: Int,
    val discoveredWordsCsv: String
)

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
        val TOTAL_USAGE_SECONDS = intPreferencesKey("total_usage_seconds")
        val TODAY_USAGE_SECONDS = intPreferencesKey("today_usage_seconds")
        val LAST_USAGE_DATE = stringPreferencesKey("last_usage_date") // yyyy-MM-dd
        val PROFILES_LIST = stringPreferencesKey("profiles_list")
        val ACTIVE_PROFILE_ID = stringPreferencesKey("active_profile_id")
        val MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
        fun voiceSourceKey(wordId: String) = stringPreferencesKey("voice_source_$wordId")
    }

    val language: Flow<String> = context.dataStore.data.map { it[Keys.LANGUAGE] ?: "en" }
    val childAge: Flow<Int> = context.dataStore.data.map { it[Keys.CHILD_AGE] ?: 2 }
    val childName: Flow<String> = context.dataStore.data.map { it[Keys.CHILD_NAME] ?: "" }
    val totalStars: Flow<Int> = context.dataStore.data.map { it[Keys.TOTAL_STARS] ?: 0 }
    val discoveredWords: Flow<Set<String>> = context.dataStore.data.map {
        (it[Keys.DISCOVERED_WORDS] ?: "").split(",").filter { id -> id.isNotBlank() }.toSet()
    }
    val totalUsageSeconds: Flow<Int> = context.dataStore.data.map { it[Keys.TOTAL_USAGE_SECONDS] ?: 0 }
    val musicEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.MUSIC_ENABLED] ?: true }

    suspend fun setMusicEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.MUSIC_ENABLED] = enabled }
    }
    val todayUsageSeconds: Flow<Int> = context.dataStore.data.map { prefs ->
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        if (prefs[Keys.LAST_USAGE_DATE] == today) prefs[Keys.TODAY_USAGE_SECONDS] ?: 0 else 0
    }

    /** باید هر چند ده ثانیه یک‌بار، وقتی اپ باز و فعاله، صدا زده بشه */
    suspend fun addUsageSeconds(seconds: Int) {
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        context.dataStore.edit { prefs ->
            val isNewDay = prefs[Keys.LAST_USAGE_DATE] != today
            val currentToday = if (isNewDay) 0 else (prefs[Keys.TODAY_USAGE_SECONDS] ?: 0)
            prefs[Keys.TODAY_USAGE_SECONDS] = currentToday + seconds
            prefs[Keys.LAST_USAGE_DATE] = today
            prefs[Keys.TOTAL_USAGE_SECONDS] = (prefs[Keys.TOTAL_USAGE_SECONDS] ?: 0) + seconds
        }
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

    // ---------- سیستم چندپروفایلی ----------

    private fun parseProfiles(raw: String): List<Profile> =
        raw.split("||").filter { it.isNotBlank() }.mapNotNull { entry ->
            val parts = entry.split("::")
            if (parts.size < 6) return@mapNotNull null
            Profile(
                id = parts[0],
                name = parts[1],
                age = parts[2].toIntOrNull() ?: 2,
                mascotId = parts[3],
                stars = parts[4].toIntOrNull() ?: 0,
                discoveredWordsCsv = parts[5]
            )
        }

    private fun serializeProfiles(profiles: List<Profile>): String =
        profiles.joinToString("||") { p ->
            listOf(p.id, p.name, p.age.toString(), p.mascotId, p.stars.toString(), p.discoveredWordsCsv)
                .joinToString("::")
        }

    val profilesList: Flow<List<Profile>> =
        context.dataStore.data.map { parseProfiles(it[Keys.PROFILES_LIST] ?: "") }

    val activeProfileId: Flow<String?> = context.dataStore.data.map { it[Keys.ACTIVE_PROFILE_ID] }

    /** پیشرفت زنده‌ی فعلی (اسم/سن/ماسکات/ستاره/کلمات) رو توی لیست پروفایل‌ها ذخیره یا آپدیت می‌کنه */
    suspend fun saveCurrentAsProfile() {
        context.dataStore.edit { prefs ->
            val name = prefs[Keys.CHILD_NAME] ?: ""
            if (name.isBlank()) return@edit // پروفایل بی‌اسم رو ذخیره نکن

            var id = prefs[Keys.ACTIVE_PROFILE_ID]
            if (id == null) {
                id = "profile_${System.currentTimeMillis()}"
                prefs[Keys.ACTIVE_PROFILE_ID] = id
            }

            val current = Profile(
                id = id,
                name = name,
                age = prefs[Keys.CHILD_AGE] ?: 2,
                mascotId = prefs[Keys.MASCOT_ID] ?: "",
                stars = prefs[Keys.TOTAL_STARS] ?: 0,
                discoveredWordsCsv = prefs[Keys.DISCOVERED_WORDS] ?: ""
            )

            val existing = parseProfiles(prefs[Keys.PROFILES_LIST] ?: "").toMutableList()
            val index = existing.indexOfFirst { it.id == id }
            if (index >= 0) existing[index] = current else existing.add(current)
            prefs[Keys.PROFILES_LIST] = serializeProfiles(existing)
        }
    }

    /** پیشرفت یه پروفایل ذخیره‌شده رو برمی‌گردونه توی وضعیت فعلی (سوییچ‌کردن) */
    suspend fun switchToProfile(id: String) {
        context.dataStore.edit { prefs ->
            val profiles = parseProfiles(prefs[Keys.PROFILES_LIST] ?: "")
            val profile = profiles.find { it.id == id } ?: return@edit
            prefs[Keys.ACTIVE_PROFILE_ID] = profile.id
            prefs[Keys.CHILD_NAME] = profile.name
            prefs[Keys.CHILD_AGE] = profile.age
            prefs[Keys.MASCOT_ID] = profile.mascotId
            prefs[Keys.TOTAL_STARS] = profile.stars
            prefs[Keys.DISCOVERED_WORDS] = profile.discoveredWordsCsv
        }
    }

    /** یه پروفایل رو کامل حذف می‌کنه */
    suspend fun deleteProfile(id: String) {
        context.dataStore.edit { prefs ->
            val profiles = parseProfiles(prefs[Keys.PROFILES_LIST] ?: "").filter { it.id != id }
            prefs[Keys.PROFILES_LIST] = serializeProfiles(profiles)
            if (prefs[Keys.ACTIVE_PROFILE_ID] == id) {
                prefs.remove(Keys.ACTIVE_PROFILE_ID)
                prefs[Keys.CHILD_NAME] = ""
            }
        }
    }

    /** شروع یه پروفایل کاملاً تازه (برای «شخص دیگه‌ای هستم» → افزودن بازیکن جدید) */
    suspend fun startNewProfile() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.ACTIVE_PROFILE_ID)
            prefs[Keys.CHILD_NAME] = ""
            prefs[Keys.CHILD_AGE] = 2
            prefs.remove(Keys.MASCOT_ID)
            prefs[Keys.TOTAL_STARS] = 0
            prefs[Keys.DISCOVERED_WORDS] = ""
        }
    }
}
