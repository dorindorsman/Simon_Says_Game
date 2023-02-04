package com.dorin.simonsaysgame.gamepanel

import androidx.compose.ui.graphics.Color
import com.dorin.simonsaysgame.menu.GameMode
import com.dorin.simonsaysgame.menu.MenuEvent


sealed class PanelGameEvent {
    object SettingsButtonClicked : PanelGameEvent()
    object PrivacyButtonClicked : PanelGameEvent()
    object TermsButtonClicked : PanelGameEvent()
    class SetButtonColorState( val color: Color) : PanelGameEvent()
    class SetButtonSound(val index:Int) : PanelGameEvent()
    class SetRewardedAdsLoadingState( val boolean: Boolean) : PanelGameEvent()
    class GameModeButtonClicked(val gameMode: Int) : PanelGameEvent()
}
