package com.dorin.simonsaysgame.ads

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dorin.simonsaysgame.gamepanel.PanelGameEvent
import com.dorin.simonsaysgame.gamepanel.PanelGameViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

    private var TAG = "Ads"
    var mRewardedAd: RewardedAd? = null
    var mInterstitialAd: InterstitialAd? = null

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
                modifier = modifier.fillMaxWidth(), factory = { context ->
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
                })
        }
    }


    fun RewardedAdsLoading(context: Context, viewModel: PanelGameViewModel) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mRewardedAd = null
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                viewModel.handleEvent(PanelGameEvent.SetRewardedAdsLoadingState(true))
                mRewardedAd = rewardedAd
            }


        })
    }


    fun RewardedAdsShow(context: Context, viewModel: PanelGameViewModel) {

        if (mRewardedAd != null) {
            mRewardedAd?.show(context as Activity, OnUserEarnedRewardListener {
                @RequiresApi(Build.VERSION_CODES.O)
                fun onUserEarnedReward(rewardItem: RewardItem) {
                    var rewardAmount = rewardItem.amount
                    var rewardType = rewardItem.type
                    Log.d("TAG", "User earned the reward.")
                    viewModel.handleEvent(PanelGameEvent.SetRewardedAdsLoadingState(false))
                    //mRewardedAd = null
                }
                onUserEarnedReward(it)
            })

        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }


    }


    fun InterstitialAdlLoading(context: Context) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Ad was failed to loaded .")
                adError?.toString()?.let { Log.d(TAG, it) }
                mInterstitialAd = null

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded .")
                mInterstitialAd = interstitialAd
            }
        })
    }

    fun InterstitialAdShow(context: Context) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(context as Activity)
            mInterstitialAd = null
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.")
        }
    }






