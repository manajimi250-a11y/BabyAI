package com.example.babyai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.babyai.audio.MusicManager
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * موزیک پس‌زمینه رو شروع می‌کنه (اگه فایلش باشه و در تنظیمات خاموش نشده باشه)
 * و یه دکمه‌ی میوت نشون می‌ده. کافیه یه‌بار توی هر صفحه صداش کنی (داخل یه Box).
 */
@Composable
fun BackgroundMusicController(trackName: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val musicManager = remember { MusicManager(context) }
    val scope = rememberCoroutineScope()
    var muted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val enabled = prefs.musicEnabled.first()
        muted = !enabled
        musicManager.start(trackName)
        musicManager.setMuted(muted)
    }

    DisposableEffect(Unit) {
        onDispose { musicManager.stop() }
    }

    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.45f))
            .clickable {
                muted = !muted
                musicManager.setMuted(muted)
                scope.launch { prefs.setMusicEnabled(!muted) }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (muted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
            contentDescription = "Mute music",
            tint = Color.White
        )
    }
}
