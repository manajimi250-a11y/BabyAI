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
    var showLullabyGate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
        photoSize = prefs.photoSize.first()
        parentalGateEnabled = prefs.parentalGateEnabled.first()
        childAge = prefs.childAge.first()
        musicEnabled = prefs.musicEnabled.first()
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
            Text(if (isFa) "تنظیمات" else "Settings", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        Text(if (isFa) "زبان" else "Language", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FilterChip(
                selected = language == "fa",
                onClick = {
                    language = "fa"
                    scope.launch { prefs.setLanguage("fa") }
                },
                label = { Text("فارسی", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                colors = chipColors
            )
            FilterChip(
                selected = language == "en",
                onClick = {
                    language = "en"
                    scope.launch { prefs.setLanguage("en") }
                },
                label = { Text("English", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                colors = chipColors
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(if (isFa) "سایز عکس‌ها" else "Photo Size", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(
                PhotoSize.SMALL to (if (isFa) "کوچک" else "Small"),
                PhotoSize.MEDIUM to (if (isFa) "متوسط" else "Medium"),
                PhotoSize.LARGE to (if (isFa) "بزرگ" else "Large")
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

        Text(if (isFa) "سن فرزندتون" else "Child's Age", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                text = if (isFa) "$childAge سال" else "$childAge yrs",
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
                text = if (isFa) "قفل والدین" else "Parental Gate",
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
                text = if (isFa) "موزیک پس‌زمینه" else "Background Music",
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
                    text = if (isFa) "داشبورد والدین" else "Parent Dashboard",
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
                    text = if (isFa) "خواب‌های طلایی" else "Golden Dreams",
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
            text = if (isFa)
                "برای ضبط صدای خودتون برای هر کلمه، وارد همون کلمه بشید و روی آیکون میکروفون بزنید."
            else
                "To record your own voice for a word, open that word and tap the microphone icon.",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
