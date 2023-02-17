package com.dorin.simonsaysgame.gamepanel

import androidx.compose.ui.graphics.Color


sealed class PanelGameEvent {
    class SetButtonColorState( val color: Color) : PanelGameEvent()
    class SetButtonSound(val index:Int) : PanelGameEvent()
    class SetRewardedAdsLoadingState( val state: Boolean) : PanelGameEvent()
    class SetInterstitialAdsLoadingState( val state: Boolean) : PanelGameEvent()
    class SetGiveRewardState( val state: RewardState) : PanelGameEvent()
    class GameModeButtonClicked(val gameMode: Int) : PanelGameEvent()
    object AskForLive : PanelGameEvent()
    object AskForHint : PanelGameEvent()
    object SetHighScore : PanelGameEvent()
}
