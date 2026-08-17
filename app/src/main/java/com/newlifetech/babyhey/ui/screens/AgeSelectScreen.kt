package com.newlifetech.babyhey.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.newlifetech.babyhey.data.UiStrings
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.ui.theme.BabyGreen
import com.newlifetech.babyhey.ui.theme.BabyOrange
import com.newlifetech.babyhey.ui.theme.BabyPurple
import com.newlifetech.babyhey.ui.theme.BabyYellow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class AgeOption(val age: Int, val emoji: String, val color: androidx.compose.ui.graphics.Color)

private val ageOptions = listOf(
    AgeOption(2, "👶", BabyYellow),
    AgeOption(3, "😊", BabyOrange),
    AgeOption(4, "😄", BabyGreen),
    AgeOption(5, "🤩", com.newlifetech.babyhey.ui.theme.BabyBlue),
    AgeOption(6, "🚀", BabyPurple),
)

@Composable
fun AgeSelectScreen(onDone: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    var language by remember { mutableStateOf("en") }
    var selectedAge by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BabyPurple, BabyOrange, BabyYellow))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))

            Text(
                text = UiStrings.t("age_prompt", language),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(Modifier.height(28.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                ageOptions.forEach { option ->
                    val isSelected = selectedAge == option.age
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedAge = option.age },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) option.color else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 10.dp else 3.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 22.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(option.emoji, fontSize = 32.sp)
                            Spacer(Modifier.width(16.dp))
                            Text(
                                text = "${option.age} ${UiStrings.t("years_old_suffix", language)}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        selectedAge?.let { prefs.setChildAge(it) }
                        onDone()
                    }
                },
                enabled = selectedAge != null,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = UiStrings.t("continue_button", language),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BabyPurple
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
