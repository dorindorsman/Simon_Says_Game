package com.dorin.simonsaysgame.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.splash.SplashScreen.Companion.LOGO_SIZE
import com.dorin.simonsaysgame.ui.theme.splashScreenBackground
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Composable
fun Logo(
    offsetState: Dp,
    alphaState: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenBackground),
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


   // BannersAds()

}



@Composable
fun BannersAds() {
    // on below line creating a variable for location.
    // on below line creating a column for our maps.
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are adding a text
        Text(
            // on below line specifying text for heading.
            text = "Google Admob Banner Ads in Android",
            // adding text alignment,
            textAlign = TextAlign.Center,
            // on below line adding text color.
            color = Color.Gray,
            // on below line adding font weight.
            fontWeight = FontWeight.Bold,
            // on below line adding padding from all sides.
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )

        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(30.dp))

        // on below line adding admob banner ads.
        AndroidView(
            // on below line specifying width for ads.
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // on below line specifying ad view.
                AdView(context).apply {
                    // on below line specifying ad size
                    this.setAdSize(AdSize.BANNER)
                    // on below line specifying ad unit id
                    // currently added a test ad unit id.
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    // calling load ad to load our ad.
                    loadAd(AdRequest.Builder().build())
                }
            }
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