package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyGreen
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyPink
import com.example.babyai.ui.theme.BabyPurple
import kotlinx.coroutines.flow.first

/**
 * صفحه‌ی «چی می‌خوای بکنی؟» — بین انتخاب ماسکات و بقیه‌ی اپ.
 */
@Composable
fun ActivityHubScreen(
    onLearnClick: () -> Unit,
    onGamesClick: () -> Unit,
    onStoriesClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var language by remember { mutableStateOf("en") }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (language == "fa") "چی می‌خوای بکنی؟" else "What do you want to do?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(32.dp))

        HubCard(
            emoji = "📚",
            title = if (language == "fa") "یادگیری هوشمند" else "Smart Learning",
            subtitle = if (language == "fa") "حیوانات، رنگ‌ها، شکل‌ها و خانواده" else "Animals, colors, shapes & family",
            colors = listOf(BabyOrange, BabyPurple),
            onClick = onLearnClick
        )

        Spacer(Modifier.height(20.dp))

        HubCard(
            emoji = "🎮",
            title = if (language == "fa") "بازی‌ها" else "Games",
            subtitle = if (language == "fa") "بازی حافظه و بازی‌های بیشتر" else "Memory match & more games",
            colors = listOf(BabyBlue, BabyPurple),
            onClick = onGamesClick
        )

        Spacer(Modifier.height(20.dp))

        HubCard(
            emoji = "📖",
            title = if (language == "fa") "داستان‌ها" else "Stories",
            subtitle = if (language == "fa") "داستان‌های تعاملی و بامزه" else "Fun interactive stories",
            colors = listOf(BabyGreen, BabyPink),
            onClick = onStoriesClick
        )
    }
}

@Composable
private fun HubCard(
    emoji: String,
    title: String,
    subtitle: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(colors))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 48.sp)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(subtitle, color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
            }
        }
    }
}
