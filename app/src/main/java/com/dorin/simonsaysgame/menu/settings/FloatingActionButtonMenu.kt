package com.dorin.simonsaysgame.menu.settings

import androidx.compose.animation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dorin.simonsaysgame.menu.MenuEvent
import com.dorin.simonsaysgame.menu.MenuViewModel
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.menu.settings.settings_menu.floatingActionMenuOptions

const val EnterAnimationDurationMilli = 200
const val ExitAnimationDurationMilli = 100

@Composable
fun FloatingActionButtonMenu(
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel,
    menu: List<FloatingActionMenuModel>
) {
    val fabSettingsState = viewModel.fabSettingsState

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionMenu(
            title = stringResource(id = R.string.settings),
            menu = menu,
            isVisible = fabSettingsState == FabSettingsState.EXPANDED
        )
        FloatingActionButton(
            modifier = Modifier
                .size(50.dp)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(25.dp)),
            onClick = {
                viewModel.handleEvent(MenuEvent.SettingsButtonClicked)
            }
        ) {
            Crossfade(targetState = fabSettingsState) { state ->
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = if (state == FabSettingsState.EXPANDED) {
                        ImageVector.vectorResource(id = R.drawable.ic_close)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.ic_settings)
                    },
                    contentDescription = stringResource(id = R.string.settings),
                    tint = Color.White,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingActionButtonMenuPreview() {
    FloatingActionButtonMenu(
        modifier = Modifier,
        viewModel = viewModel(),
        menu = floatingActionMenuOptions {})
}