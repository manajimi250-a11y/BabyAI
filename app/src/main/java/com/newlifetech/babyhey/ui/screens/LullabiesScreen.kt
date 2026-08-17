package com.newlifetech.babyhey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newlifetech.babyhey.audio.LullabyPlayer
import com.newlifetech.babyhey.data.UiStrings
import com.newlifetech.babyhey.data.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

private data class Lullaby(val resName: String, val titles: Map<String, String>) {
    fun title(lang: String): String = titles[lang] ?: titles["en"] ?: resName
}

private fun l(resName: String, vararg titles: Pair<String, String>) = Lullaby(resName, titles.toMap())

private val lullabies = listOf(
    l("lullaby_sweet_petals",
        "en" to "Sweet Petals", "fa" to "گلبرگ‌های شیرین", "sv" to "Söta kronblad", "tr" to "Tatlı Yapraklar",
        "de" to "Süße Blütenblätter", "fr" to "Doux pétales", "es" to "Pétalos dulces", "ru" to "Сладкие лепестки",
        "zh" to "甜蜜花瓣", "hi" to "मीठी पंखुड़ियाँ", "ar" to "بتلات حلوة"),
    l("lullaby_goodnight_garden",
        "en" to "Goodnight Garden", "fa" to "باغ شب‌بخیر", "sv" to "Godnattträdgården", "tr" to "İyi Geceler Bahçesi",
        "de" to "Gute-Nacht-Garten", "fr" to "Le jardin bonne nuit", "es" to "Jardín de buenas noches", "ru" to "Сад спокойной ночи",
        "zh" to "晚安花园", "hi" to "शुभरात्रि बगीचा", "ar" to "حديقة تصبح على خير"),
    l("lullaby_lalaei",
        "en" to "Lullaby", "fa" to "لالایی", "sv" to "Vaggvisa", "tr" to "Ninni",
        "de" to "Schlaflied", "fr" to "Berceuse", "es" to "Canción de cuna", "ru" to "Колыбельная",
        "zh" to "摇篮曲", "hi" to "लोरी", "ar" to "تهويدة"),
    l("lullaby_soft_slumber",
        "en" to "Soft Slumber", "fa" to "خواب نرم", "sv" to "Mjuk sömn", "tr" to "Yumuşak Uyku",
        "de" to "Sanfter Schlummer", "fr" to "Doux sommeil", "es" to "Sueño suave", "ru" to "Мягкий сон",
        "zh" to "轻柔睡眠", "hi" to "कोमल नींद", "ar" to "نوم هادئ"),
    l("lullaby_sweet_slumber",
        "en" to "Sweet Slumber", "fa" to "خواب شیرین", "sv" to "Söt sömn", "tr" to "Tatlı Uyku",
        "de" to "Süßer Schlummer", "fr" to "Doux repos", "es" to "Dulce sueño", "ru" to "Сладкий сон",
        "zh" to "甜蜜睡眠", "hi" to "मीठी नींद", "ar" to "نوم حلو"),
    l("lullaby_hushed_persian_night",
        "en" to "Hushed Persian Night", "fa" to "شب آرام", "sv" to "Tyst persisk natt", "tr" to "Sessiz İran Gecesi",
        "de" to "Stille persische Nacht", "fr" to "Nuit persane silencieuse", "es" to "Noche persa tranquila", "ru" to "Тихая персидская ночь",
        "zh" to "宁静波斯之夜", "hi" to "शांत फ़ारसी रात", "ar" to "ليلة فارسية هادئة"),
    l("lullaby_the_sleepy_world",
        "en" to "The Sleepy World", "fa" to "دنیای خواب‌آلود", "sv" to "Den sömniga världen", "tr" to "Uykulu Dünya",
        "de" to "Die verschlafene Welt", "fr" to "Le monde endormi", "es" to "El mundo somnoliento", "ru" to "Сонный мир",
        "zh" to "睡意朦胧的世界", "hi" to "नींद भरी दुनिया", "ar" to "العالم النعسان"),
    l("lullaby_petal_lullaby",
        "en" to "Petal Lullaby", "fa" to "لالایی گلبرگ", "sv" to "Kronbladsvaggvisa", "tr" to "Yaprak Ninnisi",
        "de" to "Blütenblatt-Schlaflied", "fr" to "Berceuse des pétales", "es" to "Canción de cuna de pétalos", "ru" to "Колыбельная лепестков",
        "zh" to "花瓣摇篮曲", "hi" to "पंखुड़ी लोरी", "ar" to "تهويدة البتلات"),
)

private val NightDeep = Color(0xFF141B3C)
private val NightMid = Color(0xFF2C2F6B)
private val NightAccent = Color(0xFFB39DDB)

@Composable
fun LullabiesScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val player = remember { LullabyPlayer(context) }

    var language by remember { mutableStateOf("fa") }
    var sleepTimerMinutes by remember { mutableStateOf<Int?>(null) }
    var secondsLeft by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    // شمارش معکوس تایمر خواب؛ وقتی تموم شد، پخش رو متوقف می‌کنه
    LaunchedEffect(sleepTimerMinutes) {
        val minutes = sleepTimerMinutes
        if (minutes != null) {
            secondsLeft = minutes * 60
            while ((secondsLeft ?: 0) > 0) {
                delay(1000)
                secondsLeft = (secondsLeft ?: 1) - 1
            }
            player.stop()
            secondsLeft = null
            sleepTimerMinutes = null
        }
    }

    DisposableEffect(Unit) { onDispose { player.release() } }

    val isFa = language == "fa"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(NightDeep, NightMid)))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = UiStrings.t("lullabies_title", language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = UiStrings.t("sleep_timer_label", language),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.85f)
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf(15, 30, 60).forEach { minutes ->
                FilterChip(
                    selected = sleepTimerMinutes == minutes,
                    onClick = {
                        sleepTimerMinutes = if (sleepTimerMinutes == minutes) null else minutes
                    },
                    label = { Text("$minutes ${UiStrings.t("minutes_suffix", language)}") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NightAccent,
                        selectedLabelColor = Color.White,
                        labelColor = Color.White,
                        containerColor = Color.White.copy(alpha = 0.12f)
                    )
                )
            }
        }
        secondsLeft?.let { s ->
            Spacer(Modifier.height(6.dp))
            Text(
                text = "⏳ ${s / 60}:${(s % 60).toString().padStart(2, '0')}",
                color = NightAccent,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(lullabies) { lullaby ->
                val resId = remember(lullaby.resName) {
                    context.resources.getIdentifier(lullaby.resName, "raw", context.packageName)
                }
                val isPlaying = resId != 0 && player.currentlyPlayingResId == resId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPlaying) player.stop() else if (resId != 0) player.play(resId)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPlaying) NightAccent.copy(alpha = 0.35f) else Color.White.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = lullaby.title(language),
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Stop else Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
