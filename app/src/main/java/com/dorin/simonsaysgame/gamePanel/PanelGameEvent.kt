package com.dorin.simonsaysgame.gamePanel

import com.dorin.simonsaysgame.menu.MenuEvent

sealed class PanelGameEvent {
    object SettingsButtonClicked : PanelGameEvent()
    object PrivacyButtonClicked : PanelGameEvent()
    object TermsButtonClicked : PanelGameEvent()
    class SetRewardedAdsLoadingState( val boolean: Boolean) : PanelGameEvent()

}
