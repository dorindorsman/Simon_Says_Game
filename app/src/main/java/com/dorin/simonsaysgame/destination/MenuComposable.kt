package com.dorin.simonsaysgame.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.dorin.simonsaysgame.Screens
import com.dorin.simonsaysgame.menu.MenuScreen
import com.dorin.simonsaysgame.menu.MenuViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.menuComposable(
    navigateToPanelGame: () -> Unit
) {
    composable(
        route = Screens.MENU_SCREEN,
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
            viewModel = viewModel() as MenuViewModel
        )
    }
}

