package com.example.babyai.ui.screens

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
import com.example.babyai.audio.RecordingManager
import com.example.babyai.audio.TtsManager
import com.example.babyai.data.VoiceSource
import com.example.babyai.data.PhotoSize
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.components.RecordWordDialog
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
    var showCelebration by remember { mutableStateOf(false) }
    var photoSize by remember { mutableStateOf(PhotoSize.MEDIUM) }
    var language by remember { mutableStateOf("fa") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        photoSize = prefs.photoSize.first()
        language = prefs.language.first()
        ttsManager.setLanguage(language)
    }

    DisposableEffect(Unit) {
        onDispose { ttsManager.shutdown() }
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
                text = if (language == "fa") category.nameFa else category.nameEn,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        if (language == "fa" && !ttsManager.isCurrentLanguageSupported) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "صدای فارسی روی این گوشی پشتیبانی نمی‌شه. برای هر کلمه، انگشتتون رو نگه دارید تا صدای خودتون رو ضبط کنید 🎙️",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 13.sp
                )
            }
            Spacer(Modifier.height(12.dp))
        }

        val columns = when (photoSize) {
            PhotoSize.SMALL -> 4
            PhotoSize.MEDIUM -> 3
            PhotoSize.LARGE -> 2
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            } else {
                                val text = if (language == "fa") word.nameFa else word.nameEn
                                ttsManager.speak(text)
                            }
                        }

                        if (discovered.size == category.words.size) {
                            showCelebration = true
                        }
                    },
                    onLongPress = { recordingWord = word }
                )
            }
        }
    }

    MascotCompanion(
        modifier = Modifier
            .align(Alignment.BottomEnd)
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
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    showCelebration = false
                    discovered = setOf()
                }) { Text(if (language == "fa") "دوباره بازی کن" else "Play again") }
            },
            dismissButton = {
                TextButton(onClick = onBackToMenu) { Text(if (language == "fa") "برگشت به منو" else "Back to menu") }
            },
            title = { Text(if (language == "fa") "عالی بود! 🌟" else "Great job! 🌟") },
            text = { Text(if (language == "fa") "همه‌ی کلمه‌های این دسته رو یاد گرفتی!" else "You learned all the words in this category!") }
        )
    }
    recordingWord?.let { word ->
        RecordWordDialog(
            word = word,
            recordingManager = recordingManager,
            prefs = prefs,
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
    val displayName = if (language == "fa") word.nameFa else word.nameEn

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
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(text = displayName, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
