package com.dorin.simonsaysgame.menu.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FloatingActionMenuModel(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val onItemClick: () -> Unit,
    val id: String
)