package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.audio.RecordingManager
import com.example.babyai.audio.TtsManager
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.VoiceSource
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.components.ParentalGateDialog
import com.example.babyai.ui.components.RecordWordDialog
import com.example.babyai.ui.theme.BabyGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class MemoryCard(val cardId: Int, val wordId: String, val photoResName: String)

private fun pairCountForAge(age: Int): Int = when {
    age <= 2 -> 3
    age == 3 -> 4
    age == 4 -> 5
    age == 5 -> 6
    else -> 8
}

private fun buildShuffledCards(pairCount: Int): List<MemoryCard> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val chosenWords = allWords.shuffled().take(pairCount)
    return chosenWords.flatMap { word ->
        val photo = word.photoFileNames().random().removeSuffix(".jpg")
        listOf(
            MemoryCard(cardId = 0, wordId = word.id, photoResName = photo),
            MemoryCard(cardId = 0, wordId = word.id, photoResName = photo)
        )
    }.shuffled().mapIndexed { index, c -> c.copy(cardId = index) }
}

@Composable
fun MemoryGameScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    val ttsManager = remember { TtsManager(context) }
    val recordingManager = remember { RecordingManager(context) }

    var language by remember { mutableStateOf("en") }
    var cards by remember { mutableStateOf(listOf<MemoryCard>()) }
    var flippedIds by remember { mutableStateOf(setOf<Int>()) }
    var matchedIds by remember { mutableStateOf(setOf<Int>()) }
    var showCelebration by remember { mutableStateOf(false) }
    var pairCount by remember { mutableStateOf(4) }
    var recordingWordId by remember { mutableStateOf<String?>(null) }
    var pendingRecordingWordId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        val age = prefs.childAge.first()
        pairCount = pairCountForAge(age)
        cards = buildShuffledCards(pairCount)
        ttsManager.setLanguage(language)
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
            recordingManager.release()
        }
    }

    // پخش صدای کلمه‌ی کارتی که تازه رو شده
    suspend fun playCardSound(wordId: String) {
        val word = WordRepository.wordById(wordId) ?: return
        val source = prefs.voiceSourceFor(word.id).first()
        if (source == VoiceSource.PARENT_RECORDING && recordingManager.hasRecording(word.id, language)) {
            recordingManager.play(word.id, language)
        } else if (language == "fa" && ttsManager.playBundledAudio("${word.categoryId}_${word.id}")) {
            // صدای فارسی از پیش‌ضبط‌شده پخش شد
        } else {
            val text = word.name(language)
            ttsManager.speak(text)
        }
    }

    // چک‌کردن جفت وقتی دوتا کارت باز شدن
    LaunchedEffect(flippedIds) {
        if (flippedIds.size == 2) {
            delay(700)
            val ids = flippedIds.toList()
            val c1 = cards.find { it.cardId == ids[0] }
            val c2 = cards.find { it.cardId == ids[1] }
            if (c1 != null && c2 != null && c1.wordId == c2.wordId) {
                matchedIds = matchedIds + ids
                prefs.addBonusStars(1)
            }
            flippedIds = setOf()
        }
    }

    LaunchedEffect(matchedIds) {
        if (cards.isNotEmpty() && matchedIds.size == cards.size) {
            showCelebration = true
        }
    }

    val columns = if (pairCount <= 3) 3 else 4

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
                    text = UiStrings.t("memory_title", language),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(cards) { card ->
                    val isFaceUp = card.cardId in flippedIds || card.cardId in matchedIds
                    MemoryCardTile(
                        photoResName = card.photoResName,
                        isFaceUp = isFaceUp,
                        onClick = {
                            if (card.cardId !in matchedIds && card.cardId !in flippedIds && flippedIds.size < 2) {
                                flippedIds = flippedIds + card.cardId
                                scope.launch { playCardSound(card.wordId) }
                            }
                        },
                        onLongPress = { pendingRecordingWordId = card.wordId }
                    )
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

        if (showCelebration) {
            CelebrationOverlay(
                language = language,
                starsEarned = pairCount,
                onPlayAgain = {
                    showCelebration = false
                    matchedIds = setOf()
                    flippedIds = setOf()
                    cards = buildShuffledCards(pairCount)
                },
                onBackToMenu = onBack
            )
        }

        pendingRecordingWordId?.let { wordId ->
            ParentalGateDialog(
                language = language,
                onSuccess = {
                    pendingRecordingWordId = null
                    recordingWordId = wordId
                },
                onDismiss = { pendingRecordingWordId = null }
            )
        }

        recordingWordId?.let { wordId ->
            WordRepository.wordById(wordId)?.let { word ->
                RecordWordDialog(
                    word = word,
                    recordingManager = recordingManager,
                    prefs = prefs,
                    onDismiss = { recordingWordId = null }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MemoryCardTile(
    photoResName: String,
    isFaceUp: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    val context = LocalContext.current
    val resId = remember(photoResName) {
        context.resources.getIdentifier(photoResName, "drawable", context.packageName)
    }

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(onClick = onClick, onLongClick = onLongPress),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isFaceUp) {
                if (resId != 0) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BabyGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Text("❓", fontSize = 28.sp)
                }
            }
        }
    }
}
