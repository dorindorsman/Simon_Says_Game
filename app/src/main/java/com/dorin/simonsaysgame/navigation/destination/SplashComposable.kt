package com.dorin.simonsaysgame.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.dorin.simonsaysgame.navigation.Screens.Companion.SPLASH_SCREEN
import com.dorin.simonsaysgame.navigation.destination.Destinations.EXIT_NAVIGATION_ANIMATION_TIME_MILLIS
import com.dorin.simonsaysgame.splash.SplashScreen
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToMenuScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(EXIT_NAVIGATION_ANIMATION_TIME_MILLIS)
            )
        }
    ) {
        SplashScreen(
            navigateToMenuScreen = navigateToMenuScreen
        )
    }
}