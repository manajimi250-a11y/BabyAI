package com.example.babyai.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.babyai.audio.RecordingManager
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.VoiceSource
import com.example.babyai.data.Word
import kotlinx.coroutines.launch

/**
 * دیالوگ ضبط صدای والد برای یه کلمه‌ی خاص، جدا برای فارسی/انگلیسی.
 * بعد از ضبط موفق، خودکار منبع صدای اون کلمه رو روی «صدای والد» تنظیم می‌کنه.
 */
@Composable
fun RecordWordDialog(
    word: Word,
    recordingManager: RecordingManager,
    prefs: UserPreferences,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("fa") }
    var isRecording by remember { mutableStateOf(false) }
    var hasRecording by remember(language) {
        mutableStateOf(recordingManager.hasRecording(word.id, language))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            recordingManager.startRecording(word.id, language)
            isRecording = true
        }
    }

    fun hasMicPermission() = ContextCompat.checkSelfPermission(
        context, Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("ضبط صدا برای «${word.nameFa}»") },
        text = {
            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = language == "fa",
                        onClick = { language = "fa"; hasRecording = recordingManager.hasRecording(word.id, "fa") },
                        label = { Text("فارسی") }
                    )
                    FilterChip(
                        selected = language == "en",
                        onClick = { language = "en"; hasRecording = recordingManager.hasRecording(word.id, "en") },
                        label = { Text("English") }
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (isRecording) {
                            recordingManager.stopRecording()
                            isRecording = false
                            hasRecording = true
                            scope.launch { prefs.setVoiceSourceFor(word.id, VoiceSource.PARENT_RECORDING) }
                        } else {
                            if (hasMicPermission()) {
                                recordingManager.startRecording(word.id, language)
                                isRecording = true
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        }
                    }) {
                        Icon(
                            if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
                            contentDescription = if (isRecording) "توقف ضبط" else "شروع ضبط",
                            tint = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { recordingManager.play(word.id, language) },
                        enabled = hasRecording && !isRecording
                    ) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "پخش")
                    }

                    IconButton(
                        onClick = {
                            recordingManager.deleteRecording(word.id, language)
                            hasRecording = false
                            scope.launch { prefs.setVoiceSourceFor(word.id, VoiceSource.DEVICE_TTS) }
                        },
                        enabled = hasRecording && !isRecording
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "حذف")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("بستن") }
        }
    )
}
