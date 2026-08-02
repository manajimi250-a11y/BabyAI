package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * شامل خوش‌آمدگویی، معرفی کوتاه، و دکمه‌ی انتخاب زبان (که همینجا قابل تغییره).
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

    val iconResId = remember {
        context.resources.getIdentifier("ic_launcher", "drawable", context.packageName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        // انتخاب زبان - همیشه بالای همین صفحه در دسترسه
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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

        Spacer(Modifier.height(32.dp))

        if (iconResId != 0) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Baby AI",
                modifier = Modifier
                    .size(120.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = if (language == "fa") "به بی‌بی‌ای‌آی خوش اومدی!" else "Welcome to Baby AI!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = if (language == "fa")
                "یادبگیر، بازی کن و هر روز رشد کن!"
            else
                "Learn, play and grow every day!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp)
        ) {
            Text(
                text = if (language == "fa") "شروع کن! →" else "Let's Start! →",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}
