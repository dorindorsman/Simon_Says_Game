package com.dorin.simonsaysgame.menu.settings.settings_menu

sealed class SettingsMenuEvent {
    object Privacy : SettingsMenuEvent()
    object Terms : SettingsMenuEvent()
}