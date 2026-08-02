package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class AgeOption(val age: Int, val emoji: String)

private val ageOptions = listOf(
    AgeOption(2, "👶"),
    AgeOption(3, "😊"),
    AgeOption(4, "😄"),
    AgeOption(5, "🤩"),
    AgeOption(6, "🚀"),
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            text = if (language == "fa") "چند سالته؟" else "How old are you?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(32.dp))

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
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(option.emoji, fontSize = 28.sp)
                        Spacer(Modifier.width(16.dp))
                        Text(
                            text = if (language == "fa") "${option.age} سال" else "${option.age} years old",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
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
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = if (language == "fa") "ادامه →" else "Continue →",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}
