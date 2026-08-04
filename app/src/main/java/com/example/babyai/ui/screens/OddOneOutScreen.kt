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
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

private data class OddRound(val options: List<Pair<Word, String>>, val oddIndex: Int)

private fun roundsForAge(age: Int): Int = when {
    age <= 2 -> 4
    age == 3 -> 5
    age == 4 -> 6
    age == 5 -> 7
    else -> 8
}

private fun buildRound(): OddRound {
    val categories = WordRepository.allCategories
    val sameCategory = categories.random()
    val sameWords = sameCategory.words.shuffled().take(3)
    val oddCategory = categories.filter { it.id != sameCategory.id }.random()
    val oddWord = oddCategory.words.random()

    val options = (sameWords.map { it to it.photoFileNames().random().removeSuffix(".jpg") } +
        (oddWord to oddWord.photoFileNames().random().removeSuffix(".jpg"))).shuffled()
    val oddIndex = options.indexOfFirst { it.first.id == oddWord.id }
    return OddRound(options, oddIndex)
}

@Composable
fun OddOneOutScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val ttsManager = remember { TtsManager(context) }

    var language by remember { mutableStateOf("en") }
    var totalRounds by remember { mutableStateOf(6) }
    var currentRoundIndex by remember { mutableStateOf(0) }
    var currentRound by remember { mutableStateOf(buildRound()) }
    var feedback by remember { mutableStateOf<Boolean?>(null) } // true=correct, false=wrong
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        totalRounds = roundsForAge(prefs.childAge.first())
        ttsManager.setLanguage(language)

        suspend fun speakWithRetry(text: String) {
            var attempt = 0
            while (attempt < 6) {
                ttsManager.speak(text)
                delay(250)
                if (!ttsManager.lastSpeakFailed) break
                attempt++
            }
        }

        delay(300)
        if (language == "fa") {
            val played = ttsManager.playBundledAudio("odd_one_out_prompt")
            if (!played) speakWithRetry(UiStrings.t("odd_one_out_prompt_speech", language))
        } else {
            speakWithRetry(UiStrings.t("odd_one_out_prompt_speech", language))
        }
    }

    DisposableEffect(Unit) { onDispose { ttsManager.shutdown() } }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color(0xFFF5F9C8))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = UiStrings.t("odd_one_out_title", language),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(4.dp))
            Text(
                text = "${currentRoundIndex + 1} / $totalRounds",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(currentRound.options.size) { index ->
                    val (word, photo) = currentRound.options[index]
                    val resId = remember(photo) {
                        context.resources.getIdentifier(photo, "drawable", context.packageName)
                    }
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(enabled = feedback == null) {
                                val correct = index == currentRound.oddIndex
                                feedback = correct
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        if (resId != 0) {
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = word.nameEn,
                                modifier = Modifier.fillMaxSize()
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
                kotlinx.coroutines.delay(900)
                if (correct) prefs.addBonusStars(1)
                if (currentRoundIndex + 1 >= totalRounds) {
                    showCelebration = true
                } else {
                    currentRoundIndex += 1
                    currentRound = buildRound()
                }
                feedback = null
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (correct) UiStrings.t("feedback_correct", language)
                    else UiStrings.t("feedback_next", language),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
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
                    currentRound = buildRound()
                },
                onBackToMenu = onBack
            )
        }
    }
}
