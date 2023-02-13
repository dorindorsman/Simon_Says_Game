package com.dorin.simonsaysgame.navigationdrawer

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.dorin.simonsaysgame.MenuAction

data class MenuItem(
    val id: MenuAction,
    @StringRes val title: Int,
    val icon: ImageVector,
)