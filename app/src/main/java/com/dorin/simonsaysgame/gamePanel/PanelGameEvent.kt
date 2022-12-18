package com.dorin.simonsaysgame.gamePanel

sealed class PanelGameEvent {
    object SettingsButtonClicked : PanelGameEvent()
    object PrivacyButtonClicked : PanelGameEvent()
    object TermsButtonClicked : PanelGameEvent()
    class SetRewardedAdsLoadingState( val boolean: Boolean) : PanelGameEvent()

}
