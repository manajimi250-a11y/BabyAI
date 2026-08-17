package com.newlifetech.babyhey.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * دکمه‌ی تنظیمات (چرخ‌دنده) که پشت قفل والدین قرار داره.
 * قفل والدین همیشه فعاله و قابل غیرفعال‌سازی نیست.
 */
@Composable
fun SettingsIconButton(modifier: Modifier = Modifier, language: String = "en", onSettingsClick: () -> Unit) {
    var showGate by remember { mutableStateOf(false) }

    IconButton(modifier = modifier, onClick = { showGate = true }) {
        Icon(Icons.Filled.Settings, contentDescription = "Settings")
    }

    if (showGate) {
        ParentalGateDialog(
            language = language,
            onSuccess = {
                showGate = false
                onSettingsClick()
            },
            onDismiss = { showGate = false }
        )
    }
}
