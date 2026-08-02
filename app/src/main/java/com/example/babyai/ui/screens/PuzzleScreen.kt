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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.AgeScale
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.Word
import com.example.babyai.data.WordRepository
import com.example.babyai.ui.components.CelebrationOverlay
import com.example.babyai.ui.components.MascotCompanion
import com.example.babyai.ui.theme.BabyGreen
import kotlinx.coroutines.flow.first

private fun pickPuzzleWord(): Pair<Word, String> {
    val allWords = WordRepository.allCategories.flatMap { it.words }
    val word = allWords.random()
    return word to word.photoFileNames().random().removeSuffix(".jpg")
}

@Composable
fun PuzzleScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    var language by remember { mutableStateOf("en") }
    var pieceCount by remember { mutableStateOf(4) }
    var gridDim by remember { mutableStateOf(2) }
    var puzzleWord by remember { mutableStateOf(pickPuzzleWord()) }
    var arrangement by remember { mutableStateOf(listOf<Int>()) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

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
        startPuzzle()
    }

    val resId = remember(puzzleWord) {
        context.resources.getIdentifier(puzzleWord.second, "drawable", context.packageName)
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
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(20.dp))

            val puzzleSize: Dp = 300.dp
            val pieceSize = puzzleSize / gridDim

            if (resId != 0 && arrangement.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(puzzleSize)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    arrangement.forEachIndexed { positionIndex, originalIndex ->
                        val posRow = positionIndex / gridDim
                        val posCol = positionIndex % gridDim
                        val origRow = originalIndex / gridDim
                        val origCol = originalIndex % gridDim

                        Box(
                            modifier = Modifier
                                .offset(x = pieceSize * posCol, y = pieceSize * posRow)
                                .size(pieceSize)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (selectedIndex == positionIndex) BabyGreen else Color.Transparent)
                                .clickable {
                                    if (selectedIndex == null) {
                                        selectedIndex = positionIndex
                                    } else if (selectedIndex == positionIndex) {
                                        selectedIndex = null
                                    } else {
                                        val newArrangement = arrangement.toMutableList()
                                        val a = selectedIndex!!
                                        val b = positionIndex
                                        val tmp = newArrangement[a]
                                        newArrangement[a] = newArrangement[b]
                                        newArrangement[b] = tmp
                                        arrangement = newArrangement
                                        selectedIndex = null
                                        if (newArrangement == (0 until pieceCount).toList()) {
                                            prefs.addBonusStars(3)
                                            showCelebration = true
                                        }
                                    }
                                }
                        ) {
                            Box(modifier = Modifier.size(puzzleSize).offset(x = -pieceSize * origCol, y = -pieceSize * origRow)) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.size(puzzleSize)
                                )
                            }
                        }
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
    }
}
