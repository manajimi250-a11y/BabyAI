package com.example.babyai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.Mascot
import com.example.babyai.data.MascotRepository
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MascotSelectScreen(onMascotChosen: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    var language by remember { mutableStateOf("en") }

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
        Spacer(Modifier.height(32.dp))
        Text(
            text = if (language == "fa") "دوستت رو انتخاب کن!" else "Choose your friend!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(MascotRepository.all) { mascot ->
                MascotCard(mascot = mascot, language = language) {
                    scope.launch {
                        prefs.setMascotId(mascot.id)
                        onMascotChosen()
                    }
                }
            }
        }
    }
}

@Composable
private fun MascotCard(mascot: Mascot, language: String, onClick: () -> Unit) {
    val context = LocalContext.current
    val resId = remember(mascot.drawableName) {
        context.resources.getIdentifier(mascot.drawableName, "drawable", context.packageName)
    }
    val displayName = if (language == "fa") mascot.nameFa else mascot.nameEn

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (resId != 0) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = resId),
                    contentDescription = displayName,
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            } else {
                // اگه هنوز عکس ماسکات اضافه نشده، فقط اسمش رو نشون بده
                Text(text = displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
