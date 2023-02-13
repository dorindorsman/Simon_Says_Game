package com.dorin.simonsaysgame.menu

sealed class MenuEvent {
    object SettingsButtonClicked : MenuEvent()
    class SetRewardedAdsLoadingState(val state: Boolean) : MenuEvent()
    class SetNativeAdState(val state: Boolean) : MenuEvent()
}
