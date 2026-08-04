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
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.babyai.ui.theme.BabyPurple
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

private fun buildListenRound(optionCount: Int): Pair<Word, List<Word>> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val chosen = allWords.shuffled().take(optionCount)
    val target = chosen.random()
    return target to chosen.shuffled()
}

@Composable
fun ListenAndTapScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val ttsManager = remember { TtsManager(context) }

    var language by remember { mutableStateOf("en") }
    var totalRounds by remember { mutableStateOf(6) }
    var optionCount by remember { mutableStateOf(4) }
    var currentRoundIndex by remember { mutableStateOf(0) }
    var round by remember { mutableStateOf<Pair<Word, List<Word>>?>(null) }
    var feedback by remember { mutableStateOf<Boolean?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        ttsManager.setLanguage(language)
        val age = prefs.childAge.first()
        totalRounds = AgeScale.roundsForAge(age)
        optionCount = AgeScale.optionsForAge(age).coerceAtLeast(2)
        round = buildListenRound(optionCount)
    }

    fun playTargetAudio() {
        val target = round?.first ?: return
        if (language == "fa") {
            val played = ttsManager.playBundledAudio("${target.categoryId}_${target.id}")
            if (!played) ttsManager.speak(target.nameFa)
        } else {
            ttsManager.speak(target.nameEn)
        }
    }

    LaunchedEffect(round) {
        if (round != null) {
            delay(300)
            playTargetAudio()
        }
    }

    DisposableEffect(Unit) { onDispose { ttsManager.shutdown() } }

    val currentRound = round ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color(0xFFFDE3E3))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = if (language == "fa") "گوش کن و لمس کن 🎧" else "Listen & Tap 🎧",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "${currentRoundIndex + 1} / $totalRounds",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(72.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(BabyPurple)
                    .clickable { playTargetAudio() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.VolumeUp, contentDescription = "Play", tint = androidx.compose.ui.graphics.Color.White, modifier = Modifier.size(36.dp))
            }

            Spacer(Modifier.height(20.dp))

            val columns = if (optionCount <= 3) optionCount else 3
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 110.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(currentRound.second) { word ->
                    val photo = remember(word) { word.photoFileNames().first().removeSuffix(".jpg") }
                    val resId = remember(photo) {
                        context.resources.getIdentifier(photo, "drawable", context.packageName)
                    }
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(18.dp))
                            .clickable(enabled = feedback == null) {
                                feedback = word.id == currentRound.first.id
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        if (resId != 0) {
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(0.88f)
                                    .clip(RoundedCornerShape(14.dp))
                            )
                        }
                    }
                }
            }
        }

        com.example.babyai.ui.components.BackgroundMusicController(
            trackName = "music_thinking_games",
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

        feedback?.let { correct ->
            LaunchedEffect(correct) {
                delay(800)
                if (correct) {
                    prefs.addBonusStars(1)
                    if (currentRoundIndex + 1 >= totalRounds) {
                        showCelebration = true
                    } else {
                        currentRoundIndex += 1
                        round = buildListenRound(optionCount)
                    }
                }
                feedback = null
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (correct) (if (language == "fa") "آفرین! ✅" else "Correct! ✅")
                    else (if (language == "fa") "دوباره گوش کن 🎧" else "Listen again 🎧"),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }

        if (showCelebration) {
            CelebrationOverlay(
                language = language,
                starsEarned = totalRounds,
                onPlayAgain = {
                    showCelebration = false
                    currentRoundIndex = 0
                    round = buildListenRound(optionCount)
                },
                onBackToMenu = onBack
            )
        }
    }
}
