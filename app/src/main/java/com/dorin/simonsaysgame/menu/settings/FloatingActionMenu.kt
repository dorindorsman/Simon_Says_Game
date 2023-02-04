package com.dorin.simonsaysgame.menu.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.menu.settings.settings_menu.floatingActionMenuOptions


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingActionMenu(
    modifier: Modifier = Modifier,
    title: String,
    menu: List<FloatingActionMenuModel>,
    isVisible: Boolean
) {
    AnimatedVisibility(
        modifier = Modifier,
        visible = isVisible,
        enter = fadeIn(keyframes { durationMillis = EnterAnimationDurationMilli })
                + scaleIn(keyframes { durationMillis = EnterAnimationDurationMilli }),
        exit = fadeOut(keyframes { durationMillis = ExitAnimationDurationMilli })
                + scaleOut(keyframes { durationMillis = ExitAnimationDurationMilli })
    ) {
        FloatingActionMenuContent(
            modifier = modifier,
            title = title,
            menu = menu
        )
    }
}

@Composable
fun FloatingActionMenuContent(
    modifier: Modifier,
    title: String,
    menu: List<FloatingActionMenuModel>
) {
    Column(
        modifier = modifier
            .padding(vertical = 15.dp)
            .wrapContentSize()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .heightIn(min = 32.dp)
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.subtitle2.copy(
                color = Color.Gray
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        LazyColumn {
            items(menu) { item ->
                FloatingActionMenuItem(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingActionMenuContentPreview() {
    FloatingActionMenuContent(
        modifier = Modifier,
        title = stringResource(id = R.string.settings),
        menu = floatingActionMenuOptions{},
    )
}