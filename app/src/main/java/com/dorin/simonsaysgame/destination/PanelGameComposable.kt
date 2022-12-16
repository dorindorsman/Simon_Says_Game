package com.dorin.simonsaysgame.destination

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.dorin.simonsaysgame.Screens
import com.dorin.simonsaysgame.gamePanel.PanelGameScreen
import com.dorin.simonsaysgame.gamePanel.PanelGameScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
fun NavGraphBuilder.panelGameComposable(
    navigateToMenuScreen: () -> Unit
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
            navigateToMenuScreen = navigateToMenuScreen,
            viewModel = viewModel() as PanelGameScreenViewModel
        )
    }
}

