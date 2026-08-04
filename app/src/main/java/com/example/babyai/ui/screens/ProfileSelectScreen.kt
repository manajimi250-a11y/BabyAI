package com.example.babyai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.MascotRepository
import com.example.babyai.data.Profile
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.theme.BabyOrange
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val MAX_PROFILES = 2

/**
 * صفحه‌ی انتخاب بازیکن — همیشه بعد از «Let's Start» میاد.
 * حداکثر ۲ تا بازیکن مجازه؛ هر کدوم یه آیکون دایره‌ای بامزه (عکس ماسکاتش) + اسم زیرش.
 * جای خالی = آیکون «+ افزودن». پر که شد، دیگه گزینه‌ی افزودن سوم نمیاد.
 */
@Composable
fun ProfileSelectScreen(
    onProfileChosen: () -> Unit,
    onAddNewProfile: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("en") }
    var profiles by remember { mutableStateOf(listOf<Profile>()) }
    var profileToDelete by remember { mutableStateOf<Profile?>(null) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        profiles = prefs.profilesList.first()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = UiStrings.t("profile_prompt", language),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(36.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
            for (slot in 0 until MAX_PROFILES) {
                val profile = profiles.getOrNull(slot)
                if (profile != null) {
                    PlayerSlot(
                        profile = profile,
                        onClick = {
                            scope.launch {
                                prefs.switchToProfile(profile.id)
                                onProfileChosen()
                            }
                        },
                        onDelete = { profileToDelete = profile }
                    )
                } else {
                    AddPlayerSlot(
                        language = language,
                        onClick = onAddNewProfile
                    )
                }
            }
        }
    }

    profileToDelete?.let { profile ->
        AlertDialog(
            onDismissRequest = { profileToDelete = null },
            title = { Text(UiStrings.t("delete_player_title", language)) },
            text = {
                Text(UiStrings.t("delete_player_body", language).replace("{name}", profile.name))
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        prefs.deleteProfile(profile.id)
                        profiles = prefs.profilesList.first()
                        profileToDelete = null
                    }
                }) { Text(UiStrings.t("delete_confirm", language)) }
            },
            dismissButton = {
                TextButton(onClick = { profileToDelete = null }) {
                    Text(UiStrings.t("cancel_button", language))
                }
            }
        )
    }
}

@Composable
private fun PlayerSlot(profile: Profile, onClick: () -> Unit, onDelete: () -> Unit) {
    val context = LocalContext.current
    val mascot = remember(profile.mascotId) {
        MascotRepository.all.find { it.id == profile.mascotId }
    }
    val resId = remember(mascot) {
        mascot?.let { context.resources.getIdentifier(it.drawableName, "drawable", context.packageName) } ?: 0
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (resId != 0) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = profile.name,
                    modifier = Modifier.fillMaxSize(0.85f)
                )
            } else {
                Text("🙂", fontSize = 42.sp)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
                    .clickable { onDelete() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Delete", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(profile.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AddPlayerSlot(language: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(BabyOrange.copy(alpha = 0.15f))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BabyOrange),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = UiStrings.t("add_button", language),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
