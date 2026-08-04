package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyGreen
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyPink
import com.example.babyai.ui.theme.BabyPurple
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class Balloon(val id: Int, val word: Word, val photo: String, val isTarget: Boolean, val color: Color)
private val balloonColors = listOf(BabyPink, BabyBlue, BabyOrange, BabyPurple, BabyGreen)

private fun buildBalloons(gridSize: Int, targetCount: Int): Pair<Word, List<Balloon>> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val target = allWords.random()
    val others = allWords.filter { it.id != target.id }.shuffled().take(gridSize - targetCount)

    val list = mutableListOf<Balloon>()
    repeat(targetCount) {
        list.add(Balloon(0, target, target.photoFileNames().random().removeSuffix(".jpg"), true, balloonColors.random()))
    }
    others.forEach { w ->
        list.add(Balloon(0, w, w.photoFileNames().random().removeSuffix(".jpg"), false, balloonColors.random()))
    }
    val shuffled = list.shuffled().mapIndexed { i, b -> b.copy(id = i) }
    return target to shuffled
}

@Composable
fun BalloonPopScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    val ttsManager = remember { TtsManager(context) }

    var language by remember { mutableStateOf("en") }
    var gridSize by remember { mutableStateOf(9) }
    var targetCount by remember { mutableStateOf(3) }
    var target by remember { mutableStateOf<Word?>(null) }
    var balloons by remember { mutableStateOf(listOf<Balloon>()) }
    var poppedIds by remember { mutableStateOf(setOf<Int>()) }
    var showCelebration by remember { mutableStateOf(false) }

    fun startRound() {
        val (t, b) = buildBalloons(gridSize, targetCount)
        target = t
        balloons = b
        poppedIds = setOf()
    }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        ttsManager.setLanguage(language)
        val age = prefs.childAge.first()
        gridSize = AgeScale.speedGridSizeForAge(age)
        targetCount = AgeScale.speedTargetsForAge(age)
        delay(300)
        ttsManager.speak(UiStrings.t("balloons_intro_speech", language))
        delay(1800)
        startRound()
    }

    LaunchedEffect(target) {
        target?.let { t ->
            delay(300)
            if (language == "fa") {
                val played = ttsManager.playBundledAudio("${t.categoryId}_${t.id}")
                if (!played) ttsManager.speak(t.nameFa)
            } else {
                ttsManager.speak(t.name(language))
            }
        }
    }

    DisposableEffect(Unit) { onDispose { ttsManager.shutdown() } }

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
                    text = UiStrings.t("balloons_title", language),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = UiStrings.t("find_prefix", language) + (target?.name(language) ?: ""),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${poppedIds.size} / $targetCount 🎈",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                contentPadding = PaddingValues(bottom = 110.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(balloons) { balloon ->
                    val isPopped = balloon.id in poppedIds
                    val resId = remember(balloon.photo) {
                        context.resources.getIdentifier(balloon.photo, "drawable", context.packageName)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(0.9f)
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .background(if (isPopped) Color.LightGray.copy(alpha = 0.4f) else balloon.color)
                                .clickable(enabled = !isPopped && !showCelebration) {
                                    if (balloon.isTarget) {
                                        poppedIds = poppedIds + balloon.id
                                        scope.launch { prefs.addBonusStars(1) }
                                        if (poppedIds.size >= targetCount) {
                                            showCelebration = true
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!isPopped && resId != 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(0.75f)
                                        .clip(CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = resId),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            if (isPopped) {
                                Text("💥", fontSize = 26.sp)
                            }
                        }
                        Text("🧵", fontSize = 12.sp)
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

        if (showCelebration) {
            CelebrationOverlay(
                language = language,
                starsEarned = targetCount,
                onPlayAgain = { startRound() },
                onBackToMenu = onBack
            )
        }
    }
}
