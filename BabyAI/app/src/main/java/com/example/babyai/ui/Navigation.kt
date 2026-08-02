package com.example.babyai.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.babyai.ui.screens.CategoryMenuScreen
import com.example.babyai.ui.screens.GameScreen
import com.example.babyai.ui.screens.MascotSelectScreen
import com.example.babyai.ui.screens.SettingsScreen
import com.example.babyai.ui.screens.WelcomeScreen

object Routes {
    const val WELCOME = "welcome"
    const val MASCOT_SELECT = "mascot_select"
    const val CATEGORY_MENU = "category_menu"
    const val GAME = "game/{categoryId}"
    const val SETTINGS = "settings"

    fun gameRoute(categoryId: String) = "game/$categoryId"
}

@Composable
fun BabyAiNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.WELCOME) {

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(Routes.MASCOT_SELECT) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MASCOT_SELECT) {
            MascotSelectScreen(
                onMascotChosen = {
                    navController.navigate(Routes.CATEGORY_MENU)
                }
            )
        }

        composable(Routes.CATEGORY_MENU) {
            CategoryMenuScreen(
                onCategoryChosen = { categoryId ->
                    navController.navigate(Routes.gameRoute(categoryId))
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                }
            )
        }

        composable(Routes.GAME) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            GameScreen(
                categoryId = categoryId,
                onBackToMenu = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
