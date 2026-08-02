package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.babyai.data.Category
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyPink
import com.example.babyai.ui.theme.BabyPurple
import kotlinx.coroutines.flow.first

private fun roundsForAge(age: Int): Int = when {
    age <= 2 -> 4
    age == 3 -> 5
    age == 4 -> 6
    age == 5 -> 7
    else -> 8
}

private fun colorForCategoryId(id: String): Color = when (id) {
    "animals" -> BabyOrange
    "colors" -> BabyBlue
    "shapes" -> BabyPurple
    "people" -> BabyPink
    else -> BabyOrange
}

private fun pickRoundWord(): Pair<Word, String> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val word = allWords.random()
    val photo = word.photoFileNames().random().removeSuffix(".jpg")
    return word to photo
}

@Composable
fun SortingGameScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    var language by remember { mutableStateOf("en") }
    var totalRounds by remember { mutableStateOf(6) }
    var currentRoundIndex by remember { mutableStateOf(0) }
    var currentWord by remember { mutableStateOf(pickRoundWord()) }
    var feedback by remember { mutableStateOf<Boolean?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        totalRounds = roundsForAge(prefs.childAge.first())
    }

    val resId = remember(currentWord) {
        context.resources.getIdentifier(currentWord.second, "drawable", context.packageName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = if (language == "fa") "دسته‌بندی کن 🗂️" else "Sort it out 🗂️",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "${currentRoundIndex + 1} / $totalRounds",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(24.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            WordRepository.allCategories.chunked(2).forEach { rowCategories ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    rowCategories.forEach { category ->
                        CategoryButton(
                            category = category,
                            language = language,
                            enabled = feedback == null,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                feedback = category.id == currentWord.first.categoryId
                            }
                        )
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
                if (currentRoundIndex + 1 >= totalRounds) {
                    prefs.addBonusStars(totalRounds)
                    showCelebration = true
                } else {
                    currentRoundIndex += 1
                    currentWord = pickRoundWord()
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
                    currentWord = pickRoundWord()
                },
                onBackToMenu = onBack
            )
        }
    }
}

@Composable
private fun CategoryButton(
    category: Category,
    language: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(64.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(enabled = enabled) { onClick() },
        colors = CardDefaults.cardColors(containerColor = colorForCategoryId(category.id))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = if (language == "fa") category.nameFa else category.nameEn,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
