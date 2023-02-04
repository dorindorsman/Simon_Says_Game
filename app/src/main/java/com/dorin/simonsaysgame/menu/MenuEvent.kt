package com.dorin.simonsaysgame.menu

sealed class MenuEvent {
    object SettingsButtonClicked : MenuEvent()
    class SetRewardedAdsLoadingState(val boolean: Boolean) : MenuEvent()
    object PrivacyButtonClicked : MenuEvent()
    object TermsButtonClicked : MenuEvent()
    object AboutButtonClicked : MenuEvent()
}
