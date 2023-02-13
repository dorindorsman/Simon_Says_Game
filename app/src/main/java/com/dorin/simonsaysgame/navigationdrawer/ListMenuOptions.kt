package com.dorin.simonsaysgame.navigationdrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import com.dorin.simonsaysgame.MenuAction
import com.dorin.simonsaysgame.R

@Composable
fun ListMenuOptions(): List<MenuItem> {
    return listOf(
        MenuItem(
            icon = Icons.Default.Star,
            title = R.string.premium,
            id = MenuAction.PREMIUM
        ),
        MenuItem(
            icon = Icons.Default.ShoppingCart,
            title = R.string.purchase,
            id = MenuAction.PURCHASE
        ),
        MenuItem(
            icon = Icons.Default.Lock,
            title = R.string.privacy_policy,
            id = MenuAction.PRIVACY
        ),
        MenuItem(
            icon = Icons.Default.CheckCircle,
            title = R.string.terms_of_service,
            id = MenuAction.TERMS
        ),
        MenuItem(
            icon = Icons.Default.Info,
            title = R.string.about,
            id = MenuAction.ABOUT
        )
    )
}