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
import com.example.babyai.ui.theme.BabyGreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// آبی آسمونی: پایین تیره‌تر، بالا روشن‌تر (تقریبا سفید)
private val SkyBlueDark = Color(0xFF4FA3E3)
private val SkyBlueLight = Color(0xFFF0F8FF)
private val SelectedBlue = Color(0xFF2979FF)

@Composable
fun SettingsScreen(onBack: () -> Unit, onParentDashboardClick: () -> Unit) {
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
                Icon(Icons.Filled.ArrowBack, contentDescription = "برگشت")
            }
            Text("تنظیمات", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        Text("زبان", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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

        Text("سایز عکس‌ها", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                    label = { Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                    colors = chipColors
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("سن فرزندتون", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            Text("$childAge سال", fontWeight = FontWeight.Bold, fontSize = 17.sp, modifier = Modifier.padding(start = 12.dp))
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("قفل والدین (Parental Gate)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                    text = if (language == "fa") "داشبورد والدین" else "Parent Dashboard",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = "برای ضبط صدای خودتون برای هر کلمه، وارد همون کلمه بشید و روی آیکون میکروفون بزنید.",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
