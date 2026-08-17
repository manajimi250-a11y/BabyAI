package com.newlifetech.babyhey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.newlifetech.babyhey.data.SupportedLanguages
import com.newlifetech.babyhey.data.UserPreferences
import com.newlifetech.babyhey.ui.BabyAiNavHost
import com.newlifetech.babyhey.ui.theme.BabyAITheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val prefs = remember { UserPreferences(context) }
            val nightMode by prefs.nightModeEnabled.collectAsState(initial = false)
            val language by prefs.language.collectAsState(initial = "en")
            val isRtl = language in SupportedLanguages.rtlLanguages

            BabyAITheme(nightMode = nightMode) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        UsageTracker()
                        BabyAiNavHost()
                    }
                }
            }
        }
    }
}

/**
 * از لحظه‌ای که اپ روی صفحه‌ست (RESUMED) تا لحظه‌ای که میره پس‌زمینه (PAUSED)،
 * زمان رو اندازه می‌گیره و توی تنظیمات ذخیره می‌کنه (برای داشبورد والدین).
 */
@androidx.compose.runtime.Composable
private fun UsageTracker() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val prefs = remember { UserPreferences(context) }
    val scope = remember { CoroutineScope(Dispatchers.Main) }

    DisposableEffect(lifecycleOwner) {
        var resumeTimeMs = 0L
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    resumeTimeMs = System.currentTimeMillis()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    if (resumeTimeMs > 0) {
                        val elapsedSeconds = ((System.currentTimeMillis() - resumeTimeMs) / 1000).toInt()
                        if (elapsedSeconds > 0) {
                            scope.launch { prefs.addUsageSeconds(elapsedSeconds) }
                        }
                    }
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
