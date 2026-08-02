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
import com.example.babyai.audio.TtsManager
import com.example.babyai.data.StoryRepository
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.theme.BabyYellow
import kotlinx.coroutines.flow.first

@Composable
fun StoryScreen(storyId: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val ttsManager = remember { TtsManager(context) }
    val story = remember(storyId) { StoryRepository.storyById(storyId) }

    var language by remember { mutableStateOf("en") }
    var pageIndex by remember { mutableStateOf(0) }
    var answeredCorrectly by remember { mutableStateOf(false) }
    var showCelebration by remember { mutableStateOf(false) }
    var starsEarned by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        ttsManager.setLanguage(language)
    }

    DisposableEffect(Unit) {
        onDispose { ttsManager.shutdown() }
    }

    if (story == null) return
    val page = story.pages.getOrNull(pageIndex) ?: return

    // هر بار صفحه عوض شد، متن رو با صدا بخون (اول صدای آماده، بعد TTS)
    LaunchedEffect(pageIndex) {
        answeredCorrectly = false
        if (language == "fa") {
            val played = ttsManager.playBundledAudio("story_${story.id}_page${pageIndex + 1}")
            if (!played) {
                ttsManager.speak(page.textFa)
            }
        } else {
            ttsManager.speak(page.textEn)
        }
    }

    val choiceWords = remember(pageIndex) {
        page.targetWordId?.let { targetId ->
            val target = WordRepository.wordById(targetId)
            val distractors = page.distractorWordIds.mapNotNull { WordRepository.wordById(it) }
            if (target != null) (distractors + target).shuffled() else emptyList()
        } ?: emptyList()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(
                            com.example.babyai.ui.theme.BabyYellow.copy(alpha = 0.35f),
                            com.example.babyai.ui.theme.BabyPink.copy(alpha = 0.35f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = if (language == "fa") story.titleFa else story.titleEn,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "${pageIndex + 1} / ${story.pages.size}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.weight(0.3f))

            Spacer(Modifier.height(20.dp))

            // حباب متن روایت داستان
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BabyYellow.copy(alpha = 0.25f))
            ) {
                Text(
                    text = if (language == "fa") page.textFa else page.textEn,
                    modifier = Modifier.padding(20.dp),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(28.dp))

            if (choiceWords.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    choiceWords.forEach { word ->
                        StoryChoiceCard(
                            word = word,
                            modifier = Modifier.weight(1f),
                            enabled = !answeredCorrectly,
                            onClick = {
                                if (word.id == page.targetWordId) {
                                    answeredCorrectly = true
                                    starsEarned += 1
                                }
                            }
                        )
                    }
                }
            } else {
                Spacer(Modifier.weight(1f))
            }

            Spacer(Modifier.weight(1f))

            val canProceed = choiceWords.isEmpty() || answeredCorrectly
            Button(
                onClick = {
                    if (pageIndex + 1 >= story.pages.size) {
                        showCelebration = true
                    } else {
                        pageIndex += 1
                    }
                },
                enabled = canProceed,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = if (language == "fa") "بعدی →" else "Next →",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        MascotCompanion(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 8.dp, end = 8.dp)
        )

        if (showCelebration) {
            CelebrationOverlay(
                language = language,
                starsEarned = starsEarned.coerceAtLeast(3),
                onPlayAgain = {
                    showCelebration = false
                    pageIndex = 0
                    starsEarned = 0
                },
                onBackToMenu = onBack
            )
        }
    }
}

@Composable
private fun StoryChoiceCard(word: Word, modifier: Modifier = Modifier, enabled: Boolean, onClick: () -> Unit) {
    val context = LocalContext.current
    val photo = remember(word) { word.photoFileNames().first().removeSuffix(".jpg") }
    val resId = remember(photo) {
        context.resources.getIdentifier(photo, "drawable", context.packageName)
    }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(18.dp))
            .clickable(enabled = enabled) { onClick() },
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
