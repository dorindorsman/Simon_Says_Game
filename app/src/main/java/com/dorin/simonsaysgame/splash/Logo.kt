package com.dorin.simonsaysgame.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.splash.SplashScreen.Companion.LOGO_SIZE
import com.dorin.simonsaysgame.ui.theme.SplashScreenBackground

@Composable
fun Logo(
    offsetState: Dp,
    alphaState: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.SplashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(LOGO_SIZE)
                .offset(y = offsetState)
                .alpha(alpha = alphaState),
            painter = painterResource(id = getLogo()),
            contentDescription = stringResource(R.string.app_name)
        )
    }
}



@Composable
fun getLogo(): Int {
    return if (isSystemInDarkTheme())
        R.drawable.ic_logo // Fixme - change to the app logo (dark)
    else
        R.drawable.ic_logo // Fixme - change to the app logo (light)
}