package com.dorin.simonsaysgame.navigationdrawer

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import com.dorin.simonsaysgame.MenuAction
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.UserType
import com.dorin.simonsaysgame.menu.MenuViewModel

@Composable
fun ListMenuOptions(menuViewModel: MenuViewModel): List<MenuItem> {
    Log.d("dorin", menuViewModel.userType.name)
    return listOf(
        if (menuViewModel.userType == UserType.NORMAL) {
            MenuItem(
                icon = Icons.Default.Star,
                title = R.string.premium,
                id = MenuAction.PREMIUM
            )
        } else {
            MenuItem(
                icon = Icons.Default.Star,
                title = R.string.unpremium,
                id = MenuAction.UNPREMIUM
            )
        },
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