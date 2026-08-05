package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.components.SettingsIconButton
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyGreen
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyPink
import com.example.babyai.ui.theme.BabyPurple
import com.example.babyai.ui.theme.BabyYellow
import kotlinx.coroutines.flow.first

@Composable
fun GamesMenuScreen(
    onBack: () -> Unit,
    onMemoryGameClick: () -> Unit,
    onOddOneOutClick: () -> Unit,
    onSortingGameClick: () -> Unit,
    onCountingGameClick: () -> Unit,
    onListenAndTapClick: () -> Unit,
    onSpeedTapClick: () -> Unit,
    onPuzzleClick: () -> Unit,
    onBalloonPopClick: () -> Unit,
    onSettingsClick: () -> Unit
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
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = UiStrings.t("games_title", language),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            SettingsIconButton(language = language, onSettingsClick = onSettingsClick)
        }

        Spacer(Modifier.height(20.dp))

        GameCard(
            emoji = "🧠",
            title = UiStrings.t("game_memory", language),
            color = BabyGreen,
            onClick = onMemoryGameClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "🔍",
            title = UiStrings.t("game_odd_one_out", language),
            color = BabyOrange,
            onClick = onOddOneOutClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "🗂️",
            title = UiStrings.t("game_sorting", language),
            color = BabyBlue,
            onClick = onSortingGameClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "🔢",
            title = UiStrings.t("game_counting", language),
            color = BabyPurple,
            onClick = onCountingGameClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "🎧",
            title = UiStrings.t("game_listen_tap", language),
            color = BabyPink,
            onClick = onListenAndTapClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "⚡",
            title = UiStrings.t("game_speed_tap", language),
            color = BabyYellow,
            onClick = onSpeedTapClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "🧩",
            title = UiStrings.t("game_puzzle", language),
            color = BabyGreen,
            onClick = onPuzzleClick
        )
        Spacer(Modifier.height(14.dp))
        GameCard(
            emoji = "🎈",
            title = UiStrings.t("game_balloons", language),
            color = BabyOrange,
            onClick = onBalloonPopClick
        )
    }
}

@Composable
private fun GameCard(emoji: String, title: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 38.sp)
            Spacer(Modifier.width(16.dp))
            Text(title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
