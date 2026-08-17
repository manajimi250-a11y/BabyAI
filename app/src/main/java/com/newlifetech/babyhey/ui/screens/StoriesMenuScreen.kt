package com.newlifetech.babyhey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newlifetech.babyhey.data.Story
import com.newlifetech.babyhey.data.StoryRepository
import com.newlifetech.babyhey.data.UiStrings
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.ui.components.SettingsIconButton
import com.newlifetech.babyhey.ui.theme.BabyBlue
import com.newlifetech.babyhey.ui.theme.BabyGreen
import com.newlifetech.babyhey.ui.theme.BabyPink
import kotlinx.coroutines.flow.first

private val storyColors = listOf(BabyGreen, BabyBlue, BabyPink)

@Composable
fun StoriesMenuScreen(
    onBack: () -> Unit,
    onStoryClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var language by remember { mutableStateOf("en") }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = UiStrings.t("stories_title", language),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            SettingsIconButton(language = language, onSettingsClick = onSettingsClick)
        }

        Spacer(Modifier.height(20.dp))

        StoryRepository.all.forEachIndexed { index, story ->
            StoryCard(
                story = story,
                language = language,
                color = storyColors[index % storyColors.size],
                onClick = { onStoryClick(story.id) }
            )
            Spacer(Modifier.height(14.dp))
        }
        Spacer(Modifier.height(24.dp).windowInsetsPadding(WindowInsets.navigationBars))
    }
}

@Composable
private fun StoryCard(story: Story, language: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(story.emoji, fontSize = 38.sp)
            Spacer(Modifier.width(16.dp))
            Text(
                text = story.title(language),
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
