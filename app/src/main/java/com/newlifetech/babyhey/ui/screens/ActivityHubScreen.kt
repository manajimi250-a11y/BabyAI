package com.newlifetech.babyhey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newlifetech.babyhey.data.UiStrings
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.ui.components.SettingsIconButton
import com.newlifetech.babyhey.ui.theme.BabyBlue
import com.newlifetech.babyhey.ui.theme.BabyGreen
import com.newlifetech.babyhey.ui.theme.BabyOrange
import com.newlifetech.babyhey.ui.theme.BabyPink
import com.newlifetech.babyhey.ui.theme.BabyPurple
import kotlinx.coroutines.flow.first

/**
 * صفحه‌ی «چی می‌خوای بکنی؟» — بین انتخاب ماسکات و بقیه‌ی اپ.
 */
@Composable
fun ActivityHubScreen(
    onLearnClick: () -> Unit,
    onGamesClick: () -> Unit,
    onStoriesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var language by remember { mutableStateOf("en") }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = UiStrings.t("hub_title", language),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(32.dp))

        HubCard(
            emoji = "📚",
            title = UiStrings.t("hub_learn_title", language),
            subtitle = UiStrings.t("hub_learn_subtitle", language),
            colors = listOf(BabyOrange, BabyPurple),
            onClick = onLearnClick
        )

        Spacer(Modifier.height(20.dp))

        HubCard(
            emoji = "🎮",
            title = UiStrings.t("hub_games_title", language),
            subtitle = UiStrings.t("hub_games_subtitle", language),
            colors = listOf(BabyBlue, BabyPurple),
            onClick = onGamesClick
        )

        Spacer(Modifier.height(20.dp))

        HubCard(
            emoji = "📖",
            title = UiStrings.t("hub_stories_title", language),
            subtitle = UiStrings.t("hub_stories_subtitle", language),
            colors = listOf(BabyGreen, BabyPink),
            onClick = onStoriesClick
        )
    }

    SettingsIconButton(
        language = language,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(12.dp),
        onSettingsClick = onSettingsClick
    )
    }
}

@Composable
private fun HubCard(
    emoji: String,
    title: String,
    subtitle: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(colors))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 48.sp)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(subtitle, color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
            }
        }
    }
}
