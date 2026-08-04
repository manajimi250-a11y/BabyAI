package com.example.babyai.ui.components

import android.media.MediaPlayer
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.ui.theme.BabyOrange
import com.example.babyai.ui.theme.BabyPink
import com.example.babyai.ui.theme.BabyPurple
import com.example.babyai.ui.theme.BabyYellow

private val cheerSounds = listOf("celebration_cheer1", "celebration_cheer2", "celebration_cheer3")

/**
 * صفحه‌ی جشن تمام‌صفحه وقتی همه‌ی کلمات یه دسته یاد گرفته شدن.
 * بزرگ، رنگارنگ، با ستاره‌های تکون‌خور و تعداد ستاره‌ی گرفته‌شده.
 */
@Composable
fun CelebrationOverlay(
    language: String,
    starsEarned: Int,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val context = LocalContext.current

    fun playSound(name: String) {
        val resId = context.resources.getIdentifier(name, "raw", context.packageName)
        if (resId != 0) {
            try {
                val player = MediaPlayer.create(context, resId)
                player?.setOnCompletionListener { it.release() }
                player?.start()
            } catch (_: Exception) {
            }
        }
    }

    LaunchedEffect(Unit) {
        playSound("celebration_clap")
    }

    val infiniteTransition = rememberInfiniteTransition(label = "celebration")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BabyPurple, BabyPink, BabyOrange, BabyYellow)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "🌟 🎉 🌟",
                fontSize = 64.sp,
                modifier = Modifier.scale(pulse)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = if (language == "fa") "آفرین!" else "Amazing!",
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = if (language == "fa")
                    "همه‌ی کلمه‌های این دسته رو یاد گرفتی! 🎊"
                else
                    "You learned all the words in this category! 🎊",
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(24.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = if (language == "fa") "⭐ +$starsEarned ستاره" else "⭐ +$starsEarned stars",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(36.dp))

            Button(
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = if (language == "fa") "دوباره بازی کن 🔁" else "Play again 🔁",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BabyPurple
                )
            }

            Spacer(Modifier.height(14.dp))

            OutlinedButton(
                onClick = onBackToMenu,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    text = if (language == "fa") "برگشت به منو" else "Back to menu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
