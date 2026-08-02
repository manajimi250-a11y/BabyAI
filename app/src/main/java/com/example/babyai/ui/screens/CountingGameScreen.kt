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
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyGreen
import com.example.babyai.ui.theme.BabyOrange
import kotlinx.coroutines.flow.first

private data class CountRound(val photoResName: String, val count: Int, val options: List<Int>)

private fun roundsForAge(age: Int): Int = when {
    age <= 2 -> 4
    age == 3 -> 5
    age == 4 -> 6
    age == 5 -> 7
    else -> 8
}

private fun maxCountForAge(age: Int): Int = when {
    age <= 2 -> 3
    age == 3 -> 4
    age == 4 -> 5
    age == 5 -> 6
    else -> 8
}

private fun buildCountRound(maxCount: Int): CountRound {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val word = allWords.random()
    val photo = word.photoFileNames().random().removeSuffix(".jpg")
    val count = (2..maxCount).random()

    val distractors = mutableSetOf<Int>()
    while (distractors.size < 2) {
        val candidate = (2..(maxCount + 1)).random()
        if (candidate != count) distractors.add(candidate)
    }
    val options = (distractors + count).shuffled()
    return CountRound(photo, count, options)
}

@Composable
fun CountingGameScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    var language by remember { mutableStateOf("en") }
    var totalRounds by remember { mutableStateOf(6) }
    var maxCount by remember { mutableStateOf(5) }
    var currentRoundIndex by remember { mutableStateOf(0) }
    var currentRound by remember { mutableStateOf(buildCountRound(5)) }
    var feedback by remember { mutableStateOf<Boolean?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        val age = prefs.childAge.first()
        totalRounds = roundsForAge(age)
        maxCount = maxCountForAge(age)
        currentRound = buildCountRound(maxCount)
    }

    val resId = remember(currentRound) {
        context.resources.getIdentifier(currentRound.photoResName, "drawable", context.packageName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = if (language == "fa") "بشمار چندتا! 🔢" else "Count them! 🔢",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "${currentRoundIndex + 1} / $totalRounds",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(if (currentRound.count <= 4) 2 else 3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                items(currentRound.count) {
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        if (resId != 0) {
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = if (language == "fa") "چندتا عکس می‌بینی؟" else "How many do you see?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                val colors = listOf(BabyOrange, BabyBlue, BabyGreen)
                currentRound.options.forEachIndexed { idx, number ->
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(colors[idx % colors.size])
                            .clickable(enabled = feedback == null) {
                                feedback = number == currentRound.count
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("$number", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(110.dp))
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
                if (currentRoundIndex + 1 >= totalRounds) {
                    prefs.addBonusStars(totalRounds)
                    showCelebration = true
                } else {
                    currentRoundIndex += 1
                    currentRound = buildCountRound(maxCount)
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
                    text = if (correct) (if (language == "fa") "آفرین! ✅" else "Correct! ✅")
                    else (if (language == "fa") "بیا این یکی رو ببینیم 🙂" else "Let's see this one 🙂"),
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
                    currentRound = buildCountRound(maxCount)
                },
                onBackToMenu = onBack
            )
        }
    }
}
