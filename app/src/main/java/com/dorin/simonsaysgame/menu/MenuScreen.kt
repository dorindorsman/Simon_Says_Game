package com.dorin.simonsaysgame.menu

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.ads.BannersAds
import com.dorin.simonsaysgame.ads.NativeAdsLoading


@Composable
fun MenuScreen(
    navigateToPanelGame: (GameMode) -> Unit,
    viewModel: MenuViewModel
) {

    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        val (title, menuContent, settingsButton, ads) = createRefs()

        TitleText(Modifier
            .constrainAs(title) {
                linkTo(
                    start = parent.start,
                    end = parent.end
                )
                top.linkTo(parent.top, 20.dp)
            }
            .fillMaxWidth(), 60.sp)

        MenuContent(
            viewModel = viewModel,
            modifier = Modifier.constrainAs(menuContent) {
                linkTo(
                    start = parent.start,
                    end = parent.end
                )
                top.linkTo(title.top, 180.dp)
            },
            navigateToPanelGame,
            context = context
        )

//        SettingsButton(viewModel = viewModel, modifier = Modifier
//            .constrainAs(settingsButton) {
//                end.linkTo(parent.end, 15.dp)
//                bottom.linkTo(ads.top, 15.dp)
//            })


        BannersAds(modifier = Modifier.constrainAs(ads) {
            linkTo(
                start = parent.start,
                end = parent.end
            )
            bottom.linkTo(parent.bottom)
        })

        if (viewModel.showNativeAdState) {
            NativeAdsLoading(context = context)
            viewModel.handleEvent(MenuEvent.SetNativeAdState(true))
        }

    }
}

@Composable
fun MenuContent(viewModel: MenuViewModel, modifier: Modifier, navigateToPanelGame: (GameMode) -> Unit, context: Context) {

    Column(modifier = modifier) {

        ExtendedFloatingActionButton(
            modifier = modifier
                .width(150.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            onClick = {
                navigateToPanelGame(GameMode.EASY)
            },
            text = {
                Text(
                    modifier = Modifier.width(100.dp),
                    text = stringResource(id = R.string.easy),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_empty),
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Start),
                    contentDescription = null,
                    tint = Color.Yellow
                )
            },
            shape = MaterialTheme.shapes.small
        )

        ExtendedFloatingActionButton(
            modifier = modifier
                .width(150.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            onClick = {
                navigateToPanelGame(GameMode.MEDIUM)
            },
            text = {
                Text(
                    modifier = Modifier.width(100.dp),
                    text = stringResource(id = R.string.medium),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_half),
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Start),
                    contentDescription = null,
                    tint = Color.Yellow
                )
            },
            shape = MaterialTheme.shapes.small
        )

        ExtendedFloatingActionButton(
            modifier = modifier
                .width(150.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            onClick = {
                viewModel.handleEvent(MenuEvent.SetNativeAdState(true))
                navigateToPanelGame(GameMode.HARD)
            },
            text = {
                Text(
                    modifier = Modifier.width(100.dp),
                    text = stringResource(id = R.string.hard),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_full),
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Start),
                    contentDescription = null,
                    tint = Color.Yellow
                )
            },
            shape = MaterialTheme.shapes.small
        )
    }
}


//@Composable
//fun SettingsButton(viewModel: MenuViewModel, modifier: Modifier) {
//
//    val coroutineScope = rememberCoroutineScope()
//
//    FloatingActionButtonMenu(
//        modifier = modifier,
//        viewModel = viewModel,
//        menu = floatingActionMenuOptions(LocalContext.current) {
//            coroutineScope.launch {
//                viewModel.handleEvent(it)
//            }
//        }
//    )
//
//}


@Preview
@Composable
private fun MenuScreenPreview() {

}
