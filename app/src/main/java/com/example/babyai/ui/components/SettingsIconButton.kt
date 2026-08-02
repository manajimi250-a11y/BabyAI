package com.example.babyai.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.babyai.data.UserPreferences
import kotlinx.coroutines.flow.first

/**
 * دکمه‌ی تنظیمات (چرخ‌دنده) که پشت قفل والدین قرار داره.
 * توی هر صفحه‌ای که لازم باشه، همینو صدا بزن.
 */
@Composable
fun SettingsIconButton(modifier: Modifier = Modifier, onSettingsClick: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var showGate by remember { mutableStateOf(false) }

    IconButton(modifier = modifier, onClick = { showGate = true }) {
        Icon(Icons.Filled.Settings, contentDescription = "Settings")
    }

    if (showGate) {
        var gateEnabled by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            gateEnabled = prefs.parentalGateEnabled.first()
            if (!gateEnabled) {
                showGate = false
                onSettingsClick()
            }
        }
        if (gateEnabled) {
            ParentalGateDialog(
                onSuccess = {
                    showGate = false
                    onSettingsClick()
                },
                onDismiss = { showGate = false }
            )
        }
    }
}
