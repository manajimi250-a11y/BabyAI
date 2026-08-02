package com.example.babyai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.PhotoSize
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("fa") }
    var photoSize by remember { mutableStateOf(PhotoSize.MEDIUM) }
    var parentalGateEnabled by remember { mutableStateOf(false) }
    var childAge by remember { mutableStateOf(2) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        photoSize = prefs.photoSize.first()
        parentalGateEnabled = prefs.parentalGateEnabled.first()
        childAge = prefs.childAge.first()
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "برگشت")
            }
            Text("تنظیمات", fontSize = 24.sp)
        }

        Spacer(Modifier.height(24.dp))

        Text("زبان", fontSize = 18.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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

        Spacer(Modifier.height(24.dp))

        Text("سایز عکس‌ها", fontSize = 18.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(
                PhotoSize.SMALL to "کوچک",
                PhotoSize.MEDIUM to "متوسط",
                PhotoSize.LARGE to "بزرگ"
            ).forEach { (size, label) ->
                FilterChip(
                    selected = photoSize == size,
                    onClick = {
                        photoSize = size
                        scope.launch { prefs.setPhotoSize(size) }
                    },
                    label = { Text(label) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("سن فرزندتون", fontSize = 18.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Slider(
                value = childAge.toFloat(),
                onValueChange = {
                    childAge = it.toInt()
                },
                onValueChangeFinished = {
                    scope.launch { prefs.setChildAge(childAge) }
                },
                valueRange = 1f..4f,
                steps = 2,
                modifier = Modifier.weight(1f)
            )
            Text("$childAge سال", modifier = Modifier.padding(start = 12.dp))
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("قفل والدین (Parental Gate)", fontSize = 18.sp)
            Switch(
                checked = parentalGateEnabled,
                onCheckedChange = {
                    parentalGateEnabled = it
                    scope.launch { prefs.setParentalGateEnabled(it) }
                }
            )
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = "برای ضبط صدای خودتون برای هر کلمه، وارد همون کلمه بشید و روی آیکون میکروفون بزنید.",
            fontSize = 14.sp
        )
    }
}
