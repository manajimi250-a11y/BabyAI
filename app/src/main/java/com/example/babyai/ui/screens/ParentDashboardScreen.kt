package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyGreen
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyYellow
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
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = if (language == "fa") "داشبورد والدین" else "Parent Dashboard",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(8.dp))

        if (childName.isNotBlank()) {
            Text(
                text = if (language == "fa") "گزارش پیشرفت $childName" else "$childName's progress report",
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
                label = if (language == "fa") "کل ستاره‌ها" else "Total Stars"
            )
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyGreen,
                emoji = "📖",
                value = "$discoveredCount/$totalWordsCount",
                label = if (language == "fa") "کلمات یادگرفته" else "Words Learned"
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyBlue,
                emoji = "⏱️",
                value = "${formatMinutes(todaySeconds)} " + if (language == "fa") "دقیقه" else "min",
                label = if (language == "fa") "امروز" else "Today"
            )
            StatCard(
                modifier = Modifier.weight(1f),
                color = BabyOrange,
                emoji = "📊",
                value = "${formatMinutes(totalSeconds)} " + if (language == "fa") "دقیقه" else "min",
                label = if (language == "fa") "مجموع کل" else "All Time"
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = if (language == "fa") "پیشرفت هر دسته" else "Progress by Category",
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
                        text = if (language == "fa") category.nameFa else category.nameEn,
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
