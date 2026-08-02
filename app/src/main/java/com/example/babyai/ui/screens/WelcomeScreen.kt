package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.theme.BabyOrange
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * اولین صفحه‌ای که با باز کردن اپ دیده می‌شه.
 * زدن «Let's Start» همیشه می‌ره به صفحه‌ی انتخاب بازیکن (حداکثر ۲ نفر).
 */
@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("en") }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    val bgResId = remember {
        context.resources.getIdentifier("welcome_bg", "drawable", context.packageName)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight

        if (bgResId != 0) {
            Image(
                painter = painterResource(id = bgResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            FilterChip(
                selected = language == "fa",
                onClick = {
                    language = "fa"
                    scope.launch { prefs.setLanguage("fa") }
                },
                label = { Text("فارسی", fontWeight = if (language == "fa") FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White.copy(alpha = 0.9f),
                    labelColor = Color.DarkGray,
                    selectedContainerColor = BabyOrange,
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                selected = language == "en",
                onClick = {
                    language = "en"
                    scope.launch { prefs.setLanguage("en") }
                },
                label = { Text("English", fontWeight = if (language == "en") FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White.copy(alpha = 0.9f),
                    labelColor = Color.DarkGray,
                    selectedContainerColor = BabyOrange,
                    selectedLabelColor = Color.White
                )
            )
        }

        // دکمه‌ی واقعی و شفاف، دقیقاً روی محل دکمه‌ی «Let's Start!» عکس
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight * 0.79f)
                .fillMaxWidth(0.82f)
                .height(screenHeight * 0.055f)
                .clip(RoundedCornerShape(50))
                .clickable { onStartClick() }
        )
    }
}
