package com.dorin.simonsaysgame

import androidx.navigation.NavController
import com.dorin.simonsaysgame.menu.GameMode


class Screens(navController: NavController) {

    companion object {
        const val SPLASH_SCREEN = "splash"
        const val MENU_SCREEN = "menu"
        const val PANEL_GAME_SCREEN = "game/{mode}"
        const val PANEL_GAME_ARGUMENT_KEY = "mode"
    }

    val splash: () -> Unit = {
        navController.navigate(route = MENU_SCREEN) {
            popUpTo(SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }

    val menu: (gameMode: GameMode) -> Unit = { gameMode ->
        navController.navigate(route = "game/${gameMode.ordinal}")
    }

    val game: () -> Unit = {
        navController.navigate(route = MENU_SCREEN) {
        }

    }
}