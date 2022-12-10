package com.dorin.simonsaysgame.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.dorin.simonsaysgame.Screens
import com.dorin.simonsaysgame.gamePanel.PanelGameScreen
import com.dorin.simonsaysgame.menu.MenuScreen

@ExperimentalAnimationApi
fun NavGraphBuilder.panelGameComposable(
    //navigateToPanelGame: () -> Unit
) {
    composable(
        route = Screens.PANEL_GAME_SCREEN,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = Destinations.ENTER_NAVIGATION_ANIMATION_TIME_MILLIS
                ),
                initialOffsetX = { fullWidth -> -fullWidth }
            )
        }
    ) {
        PanelGameScreen(
            //navigateToPanelGame = navigateToPanelGame
        )
    }
}

