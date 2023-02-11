package com.dorin.simonsaysgame.menu.settings.settings_menu

import android.content.Context

sealed class SettingsMenuEvent {
    class Privacy(val context: Context) : SettingsMenuEvent()
    class Terms(val context: Context)  : SettingsMenuEvent()
    class About(val openAboutDialogState: Boolean) : SettingsMenuEvent()
}