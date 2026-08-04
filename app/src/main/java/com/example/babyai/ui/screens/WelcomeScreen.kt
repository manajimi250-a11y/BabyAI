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
import androidx.compose.ui.unit.sp
import com.example.babyai.data.SupportedLanguages
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

    val bgResId = remember(language) {
        val candidate = if (language == "en") "welcome_bg" else "welcome_bg_$language"
        var id = context.resources.getIdentifier(candidate, "drawable", context.packageName)
        if (id == 0) {
            // اگه هنوز عکس این زبون ساخته نشده، برمی‌گرده به پس‌زمینه‌ی پیش‌فرض انگلیسی
            id = context.resources.getIdentifier("welcome_bg", "drawable", context.packageName)
        }
        id
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

        var menuExpanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = { menuExpanded = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.92f),
                    contentColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text("🌐", fontSize = 18.sp)
                Spacer(Modifier.width(6.dp))
                Text("زبان", fontWeight = FontWeight.Bold)
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                SupportedLanguages.codes.forEach { code ->
                    val label = SupportedLanguages.displayNames[code] ?: code
                    val flag = SupportedLanguages.flags[code] ?: ""
                    DropdownMenuItem(
                        text = {
                            Text(
                                "$flag  $label",
                                fontWeight = if (language == code) FontWeight.Bold else FontWeight.Normal,
                                color = if (language == code) BabyOrange else Color.DarkGray
                            )
                        },
                        onClick = {
                            language = code
                            scope.launch { prefs.setLanguage(code) }
                            menuExpanded = false
                        }
                    )
                }
            }
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
