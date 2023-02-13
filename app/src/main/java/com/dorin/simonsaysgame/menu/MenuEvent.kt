package com.dorin.simonsaysgame.menu

sealed class MenuEvent {
    class SetInterstitialAdsLoadingState( val state: Boolean) : MenuEvent()

}
