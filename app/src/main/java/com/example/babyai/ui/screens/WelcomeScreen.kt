package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * اولین صفحه‌ای که با باز کردن اپ دیده می‌شه.
 * عکس بچه‌ها و خورشید، تمام‌صفحه به‌عنوان پس‌زمینه؛
 * دکمه‌ی انتخاب زبان بالای صفحه و دکمه‌ی واقعی «شروع» پایین صفحه، هر دو روی عکس.
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

    Box(modifier = Modifier.fillMaxSize()) {
        if (bgResId != 0) {
            Image(
                painter = painterResource(id = bgResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // انتخاب زبان - بالای صفحه، روی عکس
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
        ) {
            FilterChip(
                selected = language == "fa",
                onClick = {
                    language = "fa"
                    scope.launch { prefs.setLanguage("fa") }
                },
                label = { Text("فارسی") }
            )
            FilterChip(
                selected = language == "en",
                onClick = {
                    language = "en"
                    scope.launch { prefs.setLanguage("en") }
                },
                label = { Text("English") }
            )
        }

        // دکمه‌ی واقعی شروع - پایین صفحه، روی عکس
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp, vertical = 36.dp)
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(29.dp)
        ) {
            Text(
                text = if (language == "fa") "شروع کن! →" else "Let's Start! →",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
