package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.PhotoSize
import com.example.babyai.data.SupportedLanguages
import com.example.babyai.data.UiStrings
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.components.ParentalGateDialog
import com.example.babyai.ui.theme.BabyGreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// آبی آسمونی: پایین تیره‌تر، بالا روشن‌تر (تقریبا سفید)
private val SkyBlueDark = Color(0xFF4FA3E3)
private val SkyBlueLight = Color(0xFFF0F8FF)
private val SelectedBlue = Color(0xFF2979FF)

@Composable
fun SettingsScreen(onBack: () -> Unit, onParentDashboardClick: () -> Unit, onLullabiesClick: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("fa") }
    var photoSize by remember { mutableStateOf(PhotoSize.MEDIUM) }
    var parentalGateEnabled by remember { mutableStateOf(false) }
    var childAge by remember { mutableStateOf(2) }
    var musicEnabled by remember { mutableStateOf(true) }
    var nightModeEnabled by remember { mutableStateOf(false) }
    var showLullabyGate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        photoSize = prefs.photoSize.first()
        parentalGateEnabled = prefs.parentalGateEnabled.first()
        childAge = prefs.childAge.first()
        musicEnabled = prefs.musicEnabled.first()
        nightModeEnabled = prefs.nightModeEnabled.first()
    }

    val isFa = language == "fa"

    val chipColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = SelectedBlue,
        selectedLabelColor = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(SkyBlueLight, SkyBlueDark))
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(UiStrings.t("settings_title", language), fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        Text(UiStrings.t("language_button", language), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        var languageMenuExpanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { languageMenuExpanded = true }) {
                Text(
                    "${SupportedLanguages.flags[language] ?: ""}  ${SupportedLanguages.displayNames[language] ?: language}",
                    fontWeight = FontWeight.Bold
                )
            }
            DropdownMenu(
                expanded = languageMenuExpanded,
                onDismissRequest = { languageMenuExpanded = false }
            ) {
                SupportedLanguages.codes.forEach { code ->
                    val label = SupportedLanguages.displayNames[code] ?: code
                    val flag = SupportedLanguages.flags[code] ?: ""
                    DropdownMenuItem(
                        text = {
                            Text(
                                "$flag  $label",
                                fontWeight = if (language == code) FontWeight.Bold else FontWeight.Normal,
                                color = if (language == code) SelectedBlue else Color.DarkGray
                            )
                        },
                        onClick = {
                            language = code
                            scope.launch { prefs.setLanguage(code) }
                            languageMenuExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(UiStrings.t("settings_photo_size", language), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(
                PhotoSize.SMALL to UiStrings.t("photo_small", language),
                PhotoSize.MEDIUM to UiStrings.t("photo_medium", language),
                PhotoSize.LARGE to UiStrings.t("photo_large", language)
            ).forEach { (size, label) ->
                FilterChip(
                    selected = photoSize == size,
                    onClick = {
                        photoSize = size
                        scope.launch { prefs.setPhotoSize(size) }
                    },
                    label = { Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                    colors = chipColors
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(UiStrings.t("settings_child_age", language), fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(thumbColor = SelectedBlue, activeTrackColor = SelectedBlue)
            )
            Text(
                text = "$childAge ${UiStrings.t("years_suffix", language)}",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = UiStrings.t("settings_parental_gate", language),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = parentalGateEnabled,
                onCheckedChange = {
                    parentalGateEnabled = it
                    scope.launch { prefs.setParentalGateEnabled(it) }
                },
                colors = SwitchDefaults.colors(checkedTrackColor = SelectedBlue)
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = UiStrings.t("settings_music", language),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = musicEnabled,
                onCheckedChange = {
                    musicEnabled = it
                    scope.launch { prefs.setMusicEnabled(it) }
                },
                colors = SwitchDefaults.colors(checkedTrackColor = SelectedBlue)
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = UiStrings.t("settings_night_mode", language),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = nightModeEnabled,
                onCheckedChange = {
                    nightModeEnabled = it
                    scope.launch { prefs.setNightModeEnabled(it) }
                },
                colors = SwitchDefaults.colors(checkedTrackColor = SelectedBlue)
            )
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onParentDashboardClick() },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = BabyGreen)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📊", fontSize = 28.sp)
                Spacer(Modifier.width(12.dp))
                Text(
                    text = UiStrings.t("settings_parent_dashboard", language),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showLullabyGate = true },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3A3D8F))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🌙", fontSize = 28.sp)
                Spacer(Modifier.width(12.dp))
                Text(
                    text = UiStrings.t("settings_golden_dreams", language),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (showLullabyGate) {
            ParentalGateDialog(
                onSuccess = {
                    showLullabyGate = false
                    onLullabiesClick()
                },
                onDismiss = { showLullabyGate = false }
            )
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = UiStrings.t("settings_record_instruction", language),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
