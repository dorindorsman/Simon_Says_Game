package com.dorin.simonsaysgame.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import com.dorin.simonsaysgame.menu.GameMode
import com.dorin.simonsaysgame.menu.MenuScreen
import com.dorin.simonsaysgame.menu.MenuViewModel
import com.dorin.simonsaysgame.navigation.Screens.Companion.MENU_SCREEN
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.menuComposable(
    navigateToPanelGame: (gameMode :GameMode) -> Unit,
    menuViewModel : MenuViewModel
) {
    composable(
        route = MENU_SCREEN,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = Destinations.ENTER_NAVIGATION_ANIMATION_TIME_MILLIS
                ),
                initialOffsetX = { fullWidth -> -fullWidth }
            )
        }
    ) {
        MenuScreen(
            navigateToPanelGame = navigateToPanelGame,
            viewModel = menuViewModel
        )
    }
}

