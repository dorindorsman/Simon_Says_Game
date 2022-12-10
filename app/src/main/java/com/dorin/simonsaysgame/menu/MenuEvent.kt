package com.dorin.simonsaysgame.menu

sealed class MenuEvent {
    object SettingsButtonClicked : MenuEvent()
    object PrivacyButtonClicked : MenuEvent()
    object TermsButtonClicked : MenuEvent()

}
