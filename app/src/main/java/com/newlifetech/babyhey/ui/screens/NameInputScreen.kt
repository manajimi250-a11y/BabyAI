package com.newlifetech.babyhey.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newlifetech.babyhey.data.UiStrings
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.ui.theme.BabyBlue
import com.newlifetech.babyhey.ui.theme.BabyOrange
import com.newlifetech.babyhey.ui.theme.BabyPink
import com.newlifetech.babyhey.ui.theme.BabyYellow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun NameInputScreen(onDone: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var language by remember { mutableStateOf("en") }
    var name by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        language = prefs.language.first()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BabyBlue, BabyPink, BabyYellow))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "🌈 ✨ 🎈", fontSize = 40.sp)

            Spacer(Modifier.height(20.dp))

            Text(
                text = UiStrings.t("name_prompt", language),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(UiStrings.t("name_label", language)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    scope.launch {
                        prefs.setChildName(name.trim())
                        onDone()
                    }
                },
                enabled = name.trim().isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BabyOrange)
            ) {
                Text(
                    text = UiStrings.t("continue_button", language),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
