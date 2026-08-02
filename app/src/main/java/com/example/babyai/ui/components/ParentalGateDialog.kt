package com.example.babyai.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

/**
 * قفل والدین: قبل از ورود به تنظیمات، یه سؤال ساده‌ی ریاضی می‌پرسه
 * که از دسترس بچه‌ی کوچیک خارج باشه ولی برای بزرگسال ساده باشه.
 */
@Composable
fun ParentalGateDialog(
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    val a = remember { Random.nextInt(3, 9) }
    val b = remember { Random.nextInt(3, 9) }
    var answer by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("تأیید والدین") },
        text = {
            Column {
                Text("برای ادامه، این جمع رو حل کنید:")
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "$a + $b = ?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = answer,
                    onValueChange = {
                        answer = it
                        showError = false
                    },
                    label = { Text("جواب") },
                    singleLine = true,
                    isError = showError
                )
                if (showError) {
                    Text(
                        text = "جواب درست نیست، دوباره امتحان کنید.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (answer.trim() == (a + b).toString()) {
                    onSuccess()
                } else {
                    showError = true
                }
            }) { Text("تأیید") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("انصراف") }
        }
    )
}
