package com.newlifetech.babyhey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newlifetech.babyhey.data.UiStrings
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.data.WordRepository
import com.newlifetech.babyhey.ui.theme.BabyBlue
import com.newlifetech.babyhey.ui.theme.BabyGreen
import com.newlifetech.babyhey.ui.theme.BabyOrange
import com.newlifetech.babyhey.ui.theme.BabyYellow
import kotlinx.coroutines.flow.first

private fun formatMinutes(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    return if (minutes < 1) "<1" else minutes.toString()
}

@Composable
fun ParentDashboardScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var language by remember { mutableStateOf("en") }
    var childName by remember { mutableStateOf("") }
    var totalStars by remember { mutableStateOf(0) }
    var todaySeconds by remember { mutableStateOf(0) }
    var totalSeconds by remember { mutableStateOf(0) }
    var discoveredWords by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        childName = prefs.childName.first()
        totalStars = prefs.totalStars.first()
        todaySeconds = prefs.todayUsageSeconds.first()
        totalSeconds = prefs.totalUsageSeconds.first()
        discoveredWords = prefs.discoveredWords.first()
    }

    val totalWordsCount = WordRepository.allCategories.sumOf { it.words.size }
    val discoveredCount = discoveredWords.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = UiStrings.t("parent_dashboard_title", language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(8.dp))

        if (childName.isNotBlank()) {
            Text(
                text = UiStrings.t("progress_report", language).replace("{name}", childName),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyYellow,
                emoji = "⭐",
                value = "$totalStars",
                label = UiStrings.t("total_stars", language)
            )
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyGreen,
                emoji = "📖",
                value = "$discoveredCount/$totalWordsCount",
                label = UiStrings.t("words_learned", language)
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyBlue,
                emoji = "⏱️",
                value = "${formatMinutes(todaySeconds)} " + UiStrings.t("minutes_suffix", language),
                label = UiStrings.t("today_label", language)
            )
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyOrange,
                emoji = "📊",
                value = "${formatMinutes(totalSeconds)} " + UiStrings.t("minutes_suffix", language),
                label = UiStrings.t("all_time_label", language)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = UiStrings.t("progress_by_category", language),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        WordRepository.allCategories.forEach { category ->
            val done = category.words.count { discoveredWords.contains(it.id) }
            val total = category.words.size
            val progress = if (total > 0) done.toFloat() / total else 0f

            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.name(language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text("$done/$total", fontSize = 14.sp)
                }
                Spacer(Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
            }
        }
    }
}

@Composable
private fun StatCard(modifier: Modifier = Modifier, color: androidx.compose.ui.graphics.Color, emoji: String, value: String, label: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 26.sp)
            Spacer(Modifier.height(4.dp))
            Text(value, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = androidx.compose.ui.graphics.Color.White)
            Text(label, fontSize = 12.sp, color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f))
        }
    }
}
