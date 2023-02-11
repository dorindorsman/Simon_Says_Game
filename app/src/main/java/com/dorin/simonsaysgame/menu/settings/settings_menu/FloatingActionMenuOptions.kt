package com.dorin.simonsaysgame.menu.settings.settings_menu

import android.content.Context
import androidx.compose.runtime.Composable
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.menu.settings.FloatingActionMenuModel


@Composable
fun floatingActionMenuOptions(context: Context, onItemClick: (SettingsMenuEvent) -> Unit): List<FloatingActionMenuModel> {
    return listOf(
        FloatingActionMenuModel(
            icon = R.drawable.ic_privacy_policy,
            title = R.string.privacy_policy,
            onItemClick = {
                onItemClick.invoke(SettingsMenuEvent.Privacy(context))
            },
            id = "btn_privacy"
        ),
        FloatingActionMenuModel(
            icon = R.drawable.ic_use_of_terms,
            title = R.string.terms_of_service,
            onItemClick = { onItemClick.invoke(SettingsMenuEvent.Terms(context)) },
            id = "btn_terms"
        ),
        FloatingActionMenuModel(
            icon = R.drawable.ic_about,
            title = R.string.about,
            onItemClick = { onItemClick.invoke(SettingsMenuEvent.About(true)) },
            id = "btn_about"
        ),
    )
}