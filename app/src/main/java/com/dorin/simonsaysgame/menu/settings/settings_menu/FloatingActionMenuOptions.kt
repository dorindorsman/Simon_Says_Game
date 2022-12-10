package com.dorin.simonsaysgame.menu.settings.settings_menu

import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.menu.settings.FloatingActionMenuModel

fun floatingActionMenuOptions(onItemClick: (SettingsMenuEvent) -> Unit): List<FloatingActionMenuModel> {
    return listOf(
        FloatingActionMenuModel(
            icon = R.drawable.ic_privacy_policy,
            title = R.string.privacy_policy,
            onItemClick = { onItemClick.invoke(SettingsMenuEvent.Privacy) },
            id = "btn_privacy"
        ),
        FloatingActionMenuModel(icon = R.drawable.ic_terms_of_service,
            title = R.string.terms_of_service,
            onItemClick = { onItemClick.invoke(SettingsMenuEvent.Terms) },
            id = "btn_terms"
        )
    )
}