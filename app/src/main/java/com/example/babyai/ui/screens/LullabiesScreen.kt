package com.example.babyai.ui.screens

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
import com.example.babyai.audio.LullabyPlayer
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

private data class Lullaby(val resName: String, val titleFa: String, val titleEn: String)

private val lullabies = listOf(
    Lullaby("lullaby_sweet_petals", "گلبرگ‌های شیرین", "Sweet Petals"),
    Lullaby("lullaby_goodnight_garden", "باغ شب‌بخیر", "Goodnight Garden"),
    Lullaby("lullaby_lalaei", "لالایی", "Lullaby"),
    Lullaby("lullaby_soft_slumber", "خواب نرم", "Soft Slumber"),
    Lullaby("lullaby_sweet_slumber", "خواب شیرین", "Sweet Slumber"),
    Lullaby("lullaby_hushed_persian_night", "شب آرام", "Hushed Persian Night"),
    Lullaby("lullaby_the_sleepy_world", "دنیای خواب‌آلود", "The Sleepy World"),
    Lullaby("lullaby_petal_lullaby", "لالایی گلبرگ", "Petal Lullaby")
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
                text = if (isFa) "خواب‌های طلایی 🌙" else "Golden Dreams 🌙",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = if (isFa) "تایمر خواب" else "Sleep Timer",
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
                    label = { Text("$minutes ${if (isFa) "دقیقه" else "min"}") },
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
                            text = if (isFa) lullaby.titleFa else lullaby.titleEn,
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
