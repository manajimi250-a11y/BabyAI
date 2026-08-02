package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.Profile
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.theme.BabyBlue
import com.example.babyai.ui.theme.BabyGreen
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyPink
import com.example.babyai.ui.theme.BabyPurple
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val profileColors = listOf(BabyOrange, BabyBlue, BabyGreen, BabyPink, BabyPurple)

/**
 * صفحه‌ی انتخاب بازیکن — اسم هر بازیکن توی یه مستطیل رنگی وسط صفحه،
 * با گزینه‌ی حذف کنار هر اسم، و دکمه‌ی «افزودن بازیکن جدید» پایین.
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = if (language == "fa") "کی بازی می‌کنه؟" else "Who's playing?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(28.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            profiles.forEachIndexed { index, profile ->
                val color = profileColors[index % profileColors.size]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(color)
                        .clickable {
                            scope.launch {
                                prefs.switchToProfile(profile.id)
                                onProfileChosen()
                            }
                        }
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = profile.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { profileToDelete = profile }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // دکمه‌ی «افزودن بازیکن جدید» - آیکون رنگی مخصوص
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .clickable { onAddNewProfile() }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BabyOrange),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PersonAdd, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(14.dp))
            Text(
                text = if (language == "fa") "شخص دیگه‌ای هستم" else "I'm someone else",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))
    }

    profileToDelete?.let { profile ->
        AlertDialog(
            onDismissRequest = { profileToDelete = null },
            title = { Text(if (language == "fa") "حذف بازیکن؟" else "Delete player?") },
            text = {
                Text(
                    if (language == "fa") "پیشرفت «${profile.name}» برای همیشه پاک می‌شه."
                    else "\"${profile.name}\"'s progress will be permanently deleted."
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        prefs.deleteProfile(profile.id)
                        profiles = prefs.profilesList.first()
                        profileToDelete = null
                    }
                }) { Text(if (language == "fa") "حذف کن" else "Delete") }
            },
            dismissButton = {
                TextButton(onClick = { profileToDelete = null }) {
                    Text(if (language == "fa") "انصراف" else "Cancel")
                }
            }
        )
    }
}
