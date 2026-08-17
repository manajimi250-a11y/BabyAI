package com.newlifetech.babyhey.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newlifetech.babyhey.audio.RecordingManager
import com.newlifetech.babyhey.audio.TtsManager
import com.newlifetech.babyhey.data.VoiceSource
import com.newlifetech.babyhey.data.PhotoSize
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.data.Word
import com.newlifetech.babyhey.data.WordRepository
import com.newlifetech.babyhey.ui.components.CelebrationOverlay
import com.newlifetech.babyhey.ui.components.MascotCompanion
import com.newlifetech.babyhey.ui.components.ParentalGateDialog
import com.newlifetech.babyhey.ui.components.RecordWordDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun GameScreen(categoryId: String, onBackToMenu: () -> Unit) {
    val context = LocalContext.current
    val category = remember(categoryId) { WordRepository.categoryById(categoryId) }
    val prefs = remember { UserPreferences(context) }
    val ttsManager = remember { TtsManager(context) }
    val recordingManager = remember { RecordingManager(context) }

    var discovered by remember { mutableStateOf(setOf<String>()) }
    var activeWord by remember { mutableStateOf<Word?>(null) }
    var recordingWord by remember { mutableStateOf<Word?>(null) }
    var pendingRecordingWord by remember { mutableStateOf<Word?>(null) }
    var showCelebration by remember { mutableStateOf(false) }
    var starsEarnedThisRound by remember { mutableStateOf(0) }
    var photoSize by remember { mutableStateOf(PhotoSize.MEDIUM) }
    var language by remember { mutableStateOf("fa") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        photoSize = prefs.photoSize.first()
        language = prefs.language.first()
        ttsManager.setLanguage(language)
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
            recordingManager.release()
        }
    }

    if (category == null) return

    val allDiscovered = discovered.size == category.words.size

    Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackToMenu) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = category.name(language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        val columns = when (photoSize) {
            PhotoSize.SMALL -> 4
            PhotoSize.MEDIUM -> 3
            PhotoSize.LARGE -> 2
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 110.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(category.words) { word ->
                WordTile(
                    word = word,
                    language = language,
                    isDiscovered = discovered.contains(word.id),
                    onTap = {
                        activeWord = word
                        discovered = discovered + word.id

                        // پخش صدا: طبق تنظیم انتخاب‌شده برای این کلمه (TTS یا صدای والد)
                        scope.launch {
                            val source = prefs.voiceSourceFor(word.id).first()
                            if (source == VoiceSource.PARENT_RECORDING &&
                                recordingManager.hasRecording(word.id, language)
                            ) {
                                recordingManager.play(word.id, language)
                            } else if (language == "fa" &&
                                ttsManager.playBundledAudio("${word.categoryId}_${word.id}")
                            ) {
                                // صدای فارسی از پیش‌ضبط‌شده پخش شد، نیازی به TTS نیست
                            } else {
                                val text = word.name(language)
                                ttsManager.speak(text)
                            }

                            val wasNew = prefs.markWordDiscovered(word.id)
                            if (wasNew) starsEarnedThisRound += 1

                            if (discovered.size == category.words.size) {
                                prefs.addBonusStars(5)
                                starsEarnedThisRound += 5
                                showCelebration = true
                            }
                        }
                    },
                    onLongPress = { pendingRecordingWord = word }
                )
            }
        }
    }

    com.newlifetech.babyhey.ui.components.BackgroundMusicController(
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

    // انیمیشن جشن کوچیک وقتی یه کلمه لمس میشه
    AnimatedVisibility(
        visible = activeWord != null,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        activeWord?.let { word ->
            LaunchedEffect(word) {
                kotlinx.coroutines.delay(900)
                activeWord = null
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (language == "fa") "آفرین! 🎉" else "Great job! 🎉",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

    if (showCelebration && allDiscovered) {
        CelebrationOverlay(
            language = language,
            starsEarned = starsEarnedThisRound,
            onPlayAgain = {
                showCelebration = false
                discovered = setOf()
                starsEarnedThisRound = 0
            },
            onBackToMenu = onBackToMenu
        )
    }
    pendingRecordingWord?.let { word ->
        ParentalGateDialog(
            language = language,
            onSuccess = {
                pendingRecordingWord = null
                recordingWord = word
            },
            onDismiss = { pendingRecordingWord = null }
        )
    }
    recordingWord?.let { word ->
        RecordWordDialog(
            word = word,
            recordingManager = recordingManager,
            prefs = prefs,
            language = language,
            onDismiss = { recordingWord = null }
        )
    }
    } // end Box
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WordTile(
    word: Word,
    language: String,
    isDiscovered: Boolean,
    onTap: () -> Unit,
    onLongPress: () -> Unit
) {
    val context = LocalContext.current
    val photoName = remember(word) { word.photoFileNames().random().removeSuffix(".jpg") }
    val resId = remember(photoName) {
        context.resources.getIdentifier(photoName, "drawable", context.packageName)
    }
    val displayName = word.name(language)

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .combinedClickable(onClick = onTap, onLongClick = onLongPress),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDiscovered) 8.dp else 2.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (resId != 0) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = displayName,
                    modifier = Modifier
                        .fillMaxSize(0.88f)
                        .clip(RoundedCornerShape(14.dp))
                )
            } else {
                Text(text = displayName, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
