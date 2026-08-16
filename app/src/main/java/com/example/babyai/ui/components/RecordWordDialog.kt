package com.example.babyai.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.babyai.data.SupportedLanguages
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.data.VoiceSource
import com.example.babyai.data.Word
import kotlinx.coroutines.launch

/**
 * دیالوگ ضبط صدای والد برای یه کلمه‌ی خاص.
 * والد می‌تونه صداش رو برای هر کدوم از ۱۱ زبون جدا ضبط کنه (چون بچه ممکنه
 * زبون اپ رو عوض کنه). بعد از ضبط موفق، خودکار منبع صدای اون کلمه/زبون رو
 * روی «صدای والد» تنظیم می‌کنه.
 */
@Composable
fun RecordWordDialog(
    word: Word,
    recordingManager: RecordingManager,
    prefs: UserPreferences,
    language: String = "en",
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var recordingLang by remember { mutableStateOf(language) }
    var isRecording by remember { mutableStateOf(false) }
    var hasRecording by remember(recordingLang) {
        mutableStateOf(recordingManager.hasRecording(word.id, recordingLang))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            recordingManager.startRecording(word.id, recordingLang)
            isRecording = true
        }
    }

    fun hasMicPermission() = ContextCompat.checkSelfPermission(
        context, Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(UiStrings.t("record_dialog_title", language) + " «${word.name(language)}»") },
        text = {
            Column {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(SupportedLanguages.codes) { code ->
                        val flag = SupportedLanguages.flags[code] ?: ""
                        FilterChip(
                            selected = recordingLang == code,
                            onClick = {
                                recordingLang = code
                                hasRecording = recordingManager.hasRecording(word.id, code)
                            },
                            label = { Text("$flag ${SupportedLanguages.displayNames[code] ?: code}") }
                        )
                    }
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
                                recordingManager.startRecording(word.id, recordingLang)
                                isRecording = true
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        }
                    }) {
                        Icon(
                            if (isRecording) Icons.Filled.Stop else Icons.Filled.Mic,
                            contentDescription = if (isRecording) "Stop" else "Record",
                            tint = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { recordingManager.play(word.id, recordingLang) },
                        enabled = hasRecording && !isRecording
                    ) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
                    }

                    IconButton(
                        onClick = {
                            recordingManager.deleteRecording(word.id, recordingLang)
                            hasRecording = false
                            scope.launch { prefs.setVoiceSourceFor(word.id, VoiceSource.DEVICE_TTS) }
                        },
                        enabled = hasRecording && !isRecording
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(UiStrings.t("record_dialog_close", language)) }
        }
    )
}
