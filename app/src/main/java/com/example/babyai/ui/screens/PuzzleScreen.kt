package com.example.babyai.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.audio.RecordingManager
import com.example.babyai.audio.TtsManager
import com.example.babyai.data.AgeScale
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.VoiceSource
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.components.RecordWordDialog
import com.example.babyai.ui.theme.BabyGreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private fun pickPuzzleWord(): Pair<Word, String> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val word = allWords.random()
    return word to word.photoFileNames().random().removeSuffix(".jpg")
}

@Composable
fun PuzzleScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    val ttsManager = remember { TtsManager(context) }
    val recordingManager = remember { RecordingManager(context) }

    var language by remember { mutableStateOf("en") }
    var pieceCount by remember { mutableStateOf(4) }
    var gridDim by remember { mutableStateOf(2) }
    var puzzleWord by remember { mutableStateOf(pickPuzzleWord()) }
    var arrangement by remember { mutableStateOf(listOf<Int>()) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var showCelebration by remember { mutableStateOf(false) }
    var showRecordDialog by remember { mutableStateOf(false) }

    fun startPuzzle() {
        puzzleWord = pickPuzzleWord()
        var shuffled: List<Int>
        do {
            shuffled = (0 until pieceCount).shuffled()
        } while (shuffled == (0 until pieceCount).toList())
        arrangement = shuffled
        selectedIndex = null
    }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        val age = prefs.childAge.first()
        pieceCount = AgeScale.puzzlePiecesForAge(age)
        gridDim = if (pieceCount <= 4) 2 else 3
        ttsManager.setLanguage(language)
        startPuzzle()
    }

    // پخش صدای کلمه‌ی پازل: صدای والد (اگه ضبط شده) یا صدای فارسی آماده یا TTS
    LaunchedEffect(puzzleWord) {
        val word = puzzleWord.first
        val source = prefs.voiceSourceFor(word.id).first()
        if (source == VoiceSource.PARENT_RECORDING && recordingManager.hasRecording(word.id, language)) {
            recordingManager.play(word.id, language)
        } else if (language == "fa" && ttsManager.playBundledAudio("${word.categoryId}_${word.id}")) {
            // صدای فارسی از پیش‌ضبط‌شده پخش شد
        } else {
            val text = if (language == "fa") word.nameFa else word.nameEn
            ttsManager.speak(text)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
            recordingManager.release()
        }
    }

    val resId = remember(puzzleWord) {
        context.resources.getIdentifier(puzzleWord.second, "drawable", context.packageName)
    }

    val bitmap = remember(resId) {
        if (resId != 0) BitmapFactory.decodeResource(context.resources, resId)?.asImageBitmap() else null
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
                    text = if (language == "fa") "پازل کوچولو 🧩" else "Little Puzzle 🧩",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showRecordDialog = true }) {
                    Icon(Icons.Filled.Mic, contentDescription = "Record")
                }
            }

            Spacer(Modifier.height(16.dp))

            val puzzleSize: Dp = 300.dp

            if (bitmap != null && arrangement.isNotEmpty()) {
                val imgW = bitmap.width
                val imgH = bitmap.height
                val srcPieceW = imgW / gridDim
                val srcPieceH = imgH / gridDim

                Box(modifier = Modifier.size(puzzleSize)) {
                    Canvas(
                        modifier = Modifier
                            .size(puzzleSize)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        val pieceSizePx = size.width / gridDim
                        arrangement.forEachIndexed { positionIndex, originalIndex ->
                            val posRow = positionIndex / gridDim
                            val posCol = positionIndex % gridDim
                            val origRow = originalIndex / gridDim
                            val origCol = originalIndex % gridDim

                            drawImage(
                                image = bitmap,
                                srcOffset = androidx.compose.ui.unit.IntOffset(origCol * srcPieceW, origRow * srcPieceH),
                                srcSize = androidx.compose.ui.unit.IntSize(srcPieceW, srcPieceH),
                                dstOffset = androidx.compose.ui.unit.IntOffset(
                                    (posCol * pieceSizePx).toInt(),
                                    (posRow * pieceSizePx).toInt()
                                ),
                                dstSize = androidx.compose.ui.unit.IntSize(pieceSizePx.toInt(), pieceSizePx.toInt())
                            )

                            drawRect(
                                color = Color.White.copy(alpha = 0.6f),
                                topLeft = Offset(posCol * pieceSizePx, posRow * pieceSizePx),
                                size = Size(pieceSizePx, pieceSizePx),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
                            )

                            if (selectedIndex == positionIndex) {
                                drawRect(
                                    color = Color(0xFF4CD964).copy(alpha = 0.35f),
                                    topLeft = Offset(posCol * pieceSizePx, posRow * pieceSizePx),
                                    size = Size(pieceSizePx, pieceSizePx)
                                )
                            }
                        }
                    }

                    val pieceDp = puzzleSize / gridDim
                    for (positionIndex in 0 until pieceCount) {
                        val posRow = positionIndex / gridDim
                        val posCol = positionIndex % gridDim
                        Box(
                            modifier = Modifier
                                .offset(x = pieceDp * posCol, y = pieceDp * posRow)
                                .size(pieceDp)
                                .clickable {
                                    if (selectedIndex == null) {
                                        selectedIndex = positionIndex
                                    } else if (selectedIndex == positionIndex) {
                                        selectedIndex = null
                                    } else {
                                        val newArrangement = arrangement.toMutableList()
                                        val a = selectedIndex!!
                                        val b = positionIndex
                                        val correctBeforeA = arrangement[a] == a
                                        val correctBeforeB = arrangement[b] == b
                                        val tmp = newArrangement[a]
                                        newArrangement[a] = newArrangement[b]
                                        newArrangement[b] = tmp
                                        arrangement = newArrangement
                                        selectedIndex = null
                                        val correctAfterA = newArrangement[a] == a
                                        val correctAfterB = newArrangement[b] == b
                                        var newlyCorrect = 0
                                        if (!correctBeforeA && correctAfterA) newlyCorrect++
                                        if (!correctBeforeB && correctAfterB) newlyCorrect++
                                        if (newlyCorrect > 0) {
                                            scope.launch { prefs.addBonusStars(newlyCorrect) }
                                        }
                                        if (newArrangement == (0 until pieceCount).toList()) {
                                            showCelebration = true
                                        }
                                    }
                                }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text(
                text = if (language == "fa") "دو تیکه رو لمس کن تا جاشون عوض بشه" else "Tap two pieces to swap them",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
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
                starsEarned = 3,
                onPlayAgain = {
                    showCelebration = false
                    startPuzzle()
                },
                onBackToMenu = onBack
            )
        }

        if (showRecordDialog) {
            RecordWordDialog(
                word = puzzleWord.first,
                recordingManager = recordingManager,
                prefs = prefs,
                onDismiss = { showRecordDialog = false }
            )
        }
    }
}
