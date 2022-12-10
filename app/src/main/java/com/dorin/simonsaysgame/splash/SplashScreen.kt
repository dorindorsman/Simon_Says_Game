package com.dorin.simonsaysgame.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dorin.simonsaysgame.splash.SplashScreen.Companion.SPLASH_SCREEN_ANIMATION_TIME_MILLIS
import com.dorin.simonsaysgame.splash.SplashScreen.Companion.SPLASH_SCREEN_TIME_MILLIS
import kotlinx.coroutines.delay

class SplashScreen {
    companion object {
        const val SPLASH_SCREEN_ANIMATION_TIME_MILLIS = 1_000
        const val SPLASH_SCREEN_TIME_MILLIS = 3_000
        val LOGO_SIZE = 200.dp
    }
}

@Composable
fun SplashScreen(
    navigateToMenuScreen: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = SPLASH_SCREEN_ANIMATION_TIME_MILLIS
        )
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = SPLASH_SCREEN_ANIMATION_TIME_MILLIS
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_TIME_MILLIS.toLong())
        navigateToMenuScreen()
    }

    Logo(offsetState = offsetState, alphaState = alphaState)
}

@Preview
@Composable
private fun SplashScreenPreview() {
    Logo(offsetState = 0.dp, alphaState = 1f)
}
