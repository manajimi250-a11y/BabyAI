package com.example.babyai.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.babyai.ui.screens.ActivityHubScreen
import com.example.babyai.ui.screens.AgeSelectScreen
import com.example.babyai.ui.screens.CategoryMenuScreen
import com.example.babyai.ui.screens.GameScreen
import com.example.babyai.ui.screens.GamesMenuScreen
import com.example.babyai.ui.screens.MascotSelectScreen
import com.example.babyai.ui.screens.MemoryGameScreen
import com.example.babyai.ui.screens.NameInputScreen
import com.example.babyai.ui.screens.ParentDashboardScreen
import com.example.babyai.ui.screens.SettingsScreen
import com.example.babyai.ui.screens.WelcomeScreen

object Routes {
    const val WELCOME = "welcome"
    const val NAME_INPUT = "name_input"
    const val AGE_SELECT = "age_select"
    const val MASCOT_SELECT = "mascot_select"
    const val ACTIVITY_HUB = "activity_hub"
    const val CATEGORY_MENU = "category_menu"
    const val GAMES_MENU = "games_menu"
    const val MEMORY_GAME = "memory_game"
    const val GAME = "game/{categoryId}"
    const val SETTINGS = "settings"
    const val PARENT_DASHBOARD = "parent_dashboard"

    fun gameRoute(categoryId: String) = "game/$categoryId"
}

@Composable
fun BabyAiNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.WELCOME) {

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onContinueAsReturningUser = {
                    navController.navigate(Routes.MASCOT_SELECT) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                },
                onStartAsNewUser = {
                    navController.navigate(Routes.NAME_INPUT) {
                        popUpTo(Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.NAME_INPUT) {
            NameInputScreen(
                onDone = {
                    navController.navigate(Routes.AGE_SELECT)
                }
            )
        }

        composable(Routes.AGE_SELECT) {
            AgeSelectScreen(
                onDone = {
                    navController.navigate(Routes.MASCOT_SELECT) {
                        popUpTo(Routes.NAME_INPUT) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MASCOT_SELECT) {
            MascotSelectScreen(
                onMascotChosen = {
                    navController.navigate(Routes.ACTIVITY_HUB)
                }
            )
        }

        composable(Routes.ACTIVITY_HUB) {
            ActivityHubScreen(
                onLearnClick = {
                    navController.navigate(Routes.CATEGORY_MENU)
                },
                onGamesClick = {
                    navController.navigate(Routes.GAMES_MENU)
                }
            )
        }

        composable(Routes.GAMES_MENU) {
            GamesMenuScreen(
                onBack = { navController.popBackStack() },
                onMemoryGameClick = {
                    navController.navigate(Routes.MEMORY_GAME)
                }
            )
        }

        composable(Routes.MEMORY_GAME) {
            MemoryGameScreen(
                onBack = { navController.popBackStack() }
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
                onBack = { navController.popBackStack() },
                onParentDashboardClick = {
                    navController.navigate(Routes.PARENT_DASHBOARD)
                }
            )
        }

        composable(Routes.PARENT_DASHBOARD) {
            ParentDashboardScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
