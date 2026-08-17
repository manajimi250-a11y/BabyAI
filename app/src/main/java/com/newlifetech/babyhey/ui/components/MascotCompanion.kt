package com.newlifetech.babyhey.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.newlifetech.babyhey.data.MascotRepository
import com.newlifetech.babyhey.data.UserPreferences
import kotlinx.coroutines.flow.first

/**
 * ماسکات انتخاب‌شده رو به‌صورت یه دایره‌ی کوچیک تو گوشه‌ی صفحه نشون می‌ده.
 * هر صفحه‌ای که بچه رو همراهی می‌کنه، همینو صدا می‌زنه.
 */
@Composable
fun MascotCompanion(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var mascotId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        mascotId = prefs.mascotId.first()
    }

    val mascot = remember(mascotId) {
        MascotRepository.all.find { it.id == mascotId }
    } ?: return

    val resId = remember(mascot.drawableName) {
        context.resources.getIdentifier(mascot.drawableName, "drawable", context.packageName)
    }
    if (resId == 0) return

    Box(
        modifier = modifier
            .size(128.dp)
            .shadow(6.dp, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = mascot.nameEn,
            modifier = Modifier
                .fillMaxSize(0.85f)
        )
    }
}
