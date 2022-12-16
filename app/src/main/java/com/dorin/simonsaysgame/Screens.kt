package com.dorin.simonsaysgame

import androidx.navigation.NavController

class Screens(navController: NavController) {

    companion object {
        const val SPLASH_SCREEN = "splash"
        const val MENU_SCREEN = "menu"
        const val PANEL_GAME_SCREEN = "panel_game"
    }

    val toMain: () -> Unit = {
        navController.navigate(route = MENU_SCREEN) {
            popUpTo(SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }

    val toGame: () -> Unit = {
        navController.navigate(route = PANEL_GAME_SCREEN)
    }



//
//    val userType: () -> Unit = { userType ->
//        when (userType) {
//            UserType.Teacher -> navController.navigate(route = TEACHER_LOGIN_SCREEN)
//            UserType.Child -> navController.navigate(route = CHILD_LOGIN_SCREEN)
//        }
//    }
//
//    val child: () -> Unit = {
//        navController.navigate(route = MAIN_SCREEN)
//    }
//
//    val teacher: () -> Unit = {
//        navController.navigate(route = MAIN_SCREEN)
//    }
}