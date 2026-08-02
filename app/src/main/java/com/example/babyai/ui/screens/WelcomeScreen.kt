package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.theme.BabyOrange
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * اولین صفحه‌ای که با باز کردن اپ دیده می‌شه.
 * عکس (بچه‌ها+خورشید+متن انگلیسی پخته‌شده) تمام‌صفحه به‌عنوان پس‌زمینه.
 *
 * اگه قبلاً اسم بچه ذخیره شده باشه:
 *   - دکمه‌ی اصلی («Let's Start!» روی عکس) = ادامه به‌عنوان همون بچه، مستقیم می‌ره داخل اپ
 *   - یه دکمه‌ی کوچیک «شخص دیگه‌ای هستم» هم نشون داده می‌شه که فلوی کامل (اسم+سن) رو دوباره شروع می‌کنه
 * اگه اسمی ذخیره نشده باشه (اولین بار): دکمه‌ی اصلی می‌ره به فلوی وارد کردن اسم.
 */
@Composable
fun WelcomeScreen(
    onContinueAsReturningUser: () -> Unit,
    onStartAsNewUser: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("en") }
    var savedName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        savedName = prefs.childName.first()
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

        // انتخاب زبان - گوشه‌ی بالا-راست، روی عکس
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
                .clickable {
                    if (savedName.isNotBlank()) onContinueAsReturningUser() else onStartAsNewUser()
                }
        )

        // اگه اسم ذخیره‌شده وجود داره، دکمه‌ی کوچیک «شخص دیگه‌ای هستم» رو نشون بده
        if (savedName.isNotBlank()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = screenHeight * 0.855f)
                    .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(20.dp))
                    .clickable { onStartAsNewUser() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = if (language == "fa") "شخص دیگه‌ای هستم" else "I'm someone else",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
        }
    }
}
