package com.dorin.simonsaysgame.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dorin.simonsaysgame.gamePanel.PanelGameEvent
import com.dorin.simonsaysgame.gamePanel.PanelGameScreenViewModel
import com.dorin.simonsaysgame.menu.MenuEvent
import com.dorin.simonsaysgame.menu.MenuViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


private var TAG = "Ads"
private var mRewardedAd: RewardedAd? = null

@Composable
fun BannersAds(modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        // on below line adding a spacer.
        Spacer(modifier = modifier.height(30.dp))

        // on below line adding admob banner ads.
        AndroidView(
            // on below line specifying width for ads.
            modifier = modifier.fillMaxWidth(),
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
fun RewardedAdsLoading(context: Context, viewModel: PanelGameScreenViewModel) {
    val adRequest = AdRequest.Builder().build()
    RewardedAd.load(
        context,
        "ca-app-pub-3940256099942544/5224354917",
        adRequest,
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
                viewModel.handleEvent(PanelGameEvent.SetRewardedAdsLoadingState(true))

            }
        })
}


@Composable
fun RewardedAdsShow(context: Context, viewModel: PanelGameScreenViewModel) {
    if (mRewardedAd != null) {
        mRewardedAd?.show(context as Activity, OnUserEarnedRewardListener() {
            fun onUserEarnedReward(rewardItem: RewardItem) {
                var rewardAmount = rewardItem.amount
                var rewardType = rewardItem.type
                Log.d(TAG, "User earned the reward.")
                viewModel.handleEvent(PanelGameEvent.SetRewardedAdsLoadingState(false))
            }
        })
    } else {
        Log.d(TAG, "The rewarded ad wasn't ready yet.")
    }
}


