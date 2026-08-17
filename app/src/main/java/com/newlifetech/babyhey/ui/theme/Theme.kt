package com.newlifetech.babyhey.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// پالت شاد و پرانرژی (طبق بریف: نه مینیمال، نه کم‌رنگ)
val BabyOrange = Color(0xFFFF7A3D)
val BabyYellow = Color(0xFFFFC93C)
val BabyBlue = Color(0xFF3FA9F5)
val BabyGreen = Color(0xFF4CD964)
val BabyPink = Color(0xFFFF6FA5)
val BabyPurple = Color(0xFF9B6BFF)
val BabyBackground = Color(0xFFFFFBF2)

// پالت ملایم «حالت شب» برای قبل خواب: تیره و گرم، نه سیاه سرد
val NightBackground = Color(0xFF221A2E)
val NightSurface = Color(0xFF2D2438)
val NightPrimary = Color(0xFFFFA46B)
val NightSecondary = Color(0xFF8FB8E8)
val NightOnBackground = Color(0xFFF0E6F5)

private val BabyAiColorScheme = lightColorScheme(
    primary = BabyOrange,
    secondary = BabyBlue,
    tertiary = BabyPink,
    background = BabyBackground,
    surface = BabyBackground,
)

private val BabyAiNightColorScheme = darkColorScheme(
    primary = NightPrimary,
    secondary = NightSecondary,
    tertiary = BabyPink,
    background = NightBackground,
    surface = NightSurface,
    onBackground = NightOnBackground,
    onSurface = NightOnBackground,
)

@Composable
fun BabyAITheme(
    nightMode: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (nightMode) BabyAiNightColorScheme else BabyAiColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
