package com.dorin.simonsaysgame.destination

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.dorin.simonsaysgame.Screens
import com.dorin.simonsaysgame.gamepanel.PanelGameEvent
import com.dorin.simonsaysgame.gamepanel.PanelGameScreen
import com.dorin.simonsaysgame.gamepanel.PanelGameViewModel
import com.dorin.simonsaysgame.menu.GameMode

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
fun NavGraphBuilder.panelGameComposable(
    navigateToMenuScreen: () -> Unit,
    panelGameViewModel : PanelGameViewModel
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
        },
        arguments = listOf(
            navArgument(Screens.PANEL_GAME_ARGUMENT_KEY){
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val gameMode = navBackStackEntry.arguments?.getInt(Screens.PANEL_GAME_ARGUMENT_KEY) ?: 0
        LaunchedEffect(key1 = gameMode){
            panelGameViewModel.handleEvent(PanelGameEvent.GameModeButtonClicked(gameMode = gameMode))
        }
        PanelGameScreen(
            navigateToMenuScreen = navigateToMenuScreen,
            viewModel = panelGameViewModel
        )
    }
}

