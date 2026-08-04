package com.example.babyai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.babyai.data.UserPreferences
import com.example.babyai.ui.theme.BabyYellow
import kotlinx.coroutines.flow.first

/**
 * نشان زرد ⭐ که تعداد کل ستاره‌ها رو نشون می‌ده — برای بالای هر صفحه.
 */
@Composable
fun StarBadge(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    var totalStars by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        totalStars = prefs.totalStars.first()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(BabyYellow, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text("⭐", fontSize = 18.sp)
        Spacer(Modifier.width(4.dp))
        Text(
            text = "$totalStars",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
