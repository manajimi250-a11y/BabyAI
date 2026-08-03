package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.audio.TtsManager
import com.example.babyai.data.AgeScale
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.theme.BabyOrange
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class SpeedCell(val cellId: Int, val word: Word, val photo: String, val isTarget: Boolean)

private fun buildSpeedRound(gridSize: Int, targetCount: Int): Pair<Word, List<SpeedCell>> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val target = allWords.random()
    val others = allWords.filter { it.id != target.id }.shuffled().take(gridSize - targetCount)

    val cells = mutableListOf<SpeedCell>()
    repeat(targetCount) {
        cells.add(SpeedCell(0, target, target.photoFileNames().random().removeSuffix(".jpg"), true))
    }
    others.forEach { w ->
        cells.add(SpeedCell(0, w, w.photoFileNames().random().removeSuffix(".jpg"), false))
    }
    val shuffled = cells.shuffled().mapIndexed { i, c -> c.copy(cellId = i) }
    return target to shuffled
}

@Composable
fun SpeedTapScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    val ttsManager = remember { TtsManager(context) }

    var language by remember { mutableStateOf("en") }
    var gridSize by remember { mutableStateOf(9) }
    var targetCount by remember { mutableStateOf(3) }
    var timeLimit by remember { mutableStateOf(25) }
    var timeLeft by remember { mutableStateOf(25) }
    var target by remember { mutableStateOf<Word?>(null) }
    var cells by remember { mutableStateOf(listOf<SpeedCell>()) }
    var foundIds by remember { mutableStateOf(setOf<Int>()) }
    var showCelebration by remember { mutableStateOf(false) }
    var timeUp by remember { mutableStateOf(false) }
    var debugLog by remember { mutableStateOf("no taps yet") }

    fun startRound() {
        val (t, c) = buildSpeedRound(gridSize, targetCount)
        target = t
        cells = c
        foundIds = setOf()
        timeLeft = timeLimit
        timeUp = false
    }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        ttsManager.setLanguage(language)
        val age = prefs.childAge.first()
        gridSize = AgeScale.speedGridSizeForAge(age)
        targetCount = AgeScale.speedTargetsForAge(age)
        timeLimit = AgeScale.speedTimeLimitSeconds(age)
        debugLog = "age=$age grid=$gridSize target=$targetCount"
        startRound()
    }

    LaunchedEffect(target) {
        target?.let { t ->
            delay(300)
            if (language == "fa") {
                val played = ttsManager.playBundledAudio("${t.categoryId}_${t.id}")
                if (!played) ttsManager.speak(t.nameFa)
            } else {
                ttsManager.speak(t.nameEn)
            }
        }
    }

    // تایمر شمارش معکوس
    LaunchedEffect(target, timeUp, showCelebration) {
        if (target != null && !timeUp && !showCelebration) {
            while (timeLeft > 0 && foundIds.size < targetCount) {
                delay(1000)
                timeLeft -= 1
            }
            if (timeLeft <= 0 && foundIds.size < targetCount) {
                timeUp = true
            }
        }
    }

    DisposableEffect(Unit) { onDispose { ttsManager.shutdown() } }

    val columns = when {
        gridSize <= 6 -> 3
        gridSize <= 9 -> 3
        else -> 4
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = if (language == "fa") "لمس سریع ⚡" else "Speed Tap ⚡",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (language == "fa") "پیدا کن: ${target?.nameFa ?: ""}" else "Find: ${target?.nameEn ?: ""}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "⏱️ $timeLeft",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (timeLeft <= 5) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.height(4.dp))
            Text(
                text = "${foundIds.size} / $targetCount",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = "DEBUG: $debugLog",
                fontSize = 10.sp,
                color = androidx.compose.ui.graphics.Color.Red
            )

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 110.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cells) { cell ->
                    val isFound = cell.cellId in foundIds
                    val resId = remember(cell.photo) {
                        context.resources.getIdentifier(cell.photo, "drawable", context.packageName)
                    }
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(enabled = !isFound && !timeUp && !showCelebration) {
                                debugLog = "cfg:grid=$gridSize,target=$targetCount | tap id=${cell.cellId} isTarget=${cell.isTarget} total_before=${foundIds.size}"
                                if (cell.isTarget) {
                                    foundIds = foundIds + cell.cellId
                                    if (foundIds.size >= targetCount) {
                                        scope.launch { prefs.addBonusStars(targetCount) }
                                        showCelebration = true
                                    }
                                }
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Box {
                            if (resId != 0) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            if (isFound) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(BabyOrange.copy(alpha = 0.55f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("✅", fontSize = 28.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        com.example.babyai.ui.components.BackgroundMusicController(
            trackName = "music_speed_games",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(20.dp)
        )

        MascotCompanion(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(20.dp)
        )

        if (timeUp) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(shape = RoundedCornerShape(24.dp)) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (language == "fa") "وقت تموم شد! ⏰" else "Time's up! ⏰",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { startRound() }) {
                            Text(if (language == "fa") "دوباره امتحان کن" else "Try again")
                        }
                    }
                }
            }
        }

        if (showCelebration) {
            CelebrationOverlay(
                language = language,
                starsEarned = targetCount,
                onPlayAgain = { startRound() },
                onBackToMenu = onBack
            )
        }

        Text(
            text = "DBG: $debugLog",
            fontSize = 11.sp,
            color = Color.Yellow,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.Black)
                .padding(4.dp)
        )
    }
}
