package com.dorin.simonsaysgame

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.dorin.simonsaysgame.Screens.Companion.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.dorin.simonsaysgame.destination.splashComposable
import com.dorin.simonsaysgame.destination.menuComposable
import com.dorin.simonsaysgame.destination.panelGameComposable
import com.dorin.simonsaysgame.gamepanel.PanelGameViewModel
import com.dorin.simonsaysgame.menu.MenuViewModel


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController,
    menuViewModel : MenuViewModel,
    panelGameViewModel : PanelGameViewModel
    ) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToMenuScreen = screen.splash
        )
        menuComposable(
            navigateToPanelGame = screen.menu,
            menuViewModel = menuViewModel
        )
        panelGameComposable(
            navigateToMenuScreen = screen.game,
            panelGameViewModel = panelGameViewModel
        )
    }
}