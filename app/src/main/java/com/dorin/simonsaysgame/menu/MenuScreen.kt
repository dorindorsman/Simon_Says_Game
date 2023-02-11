package com.dorin.simonsaysgame.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.ads.BannersAds
import com.dorin.simonsaysgame.menu.settings.FloatingActionButtonMenu
import com.dorin.simonsaysgame.menu.settings.settings_menu.SettingsMenuEvent
import com.dorin.simonsaysgame.menu.settings.settings_menu.floatingActionMenuOptions
import com.dorin.simonsaysgame.ui.theme.Green
import com.dorin.simonsaysgame.ui.theme.LightBrightBlue
import com.dorin.simonsaysgame.ui.theme.Red
import com.dorin.simonsaysgame.ui.theme.Yellow
import kotlinx.coroutines.launch



@Composable
fun MenuScreen(
    navigateToPanelGame: (GameMode) -> Unit,
    viewModel: MenuViewModel
) {

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
            .fillMaxWidth())

        MenuContent(
            viewModel = viewModel, modifier = Modifier.constrainAs(menuContent) {
                linkTo(
                    start = parent.start,
                    end = parent.end
                )
                top.linkTo(title.top, 180.dp)
            }, navigateToPanelGame
        )

        SettingsButton(viewModel = viewModel, modifier = Modifier
            .constrainAs(settingsButton) {
                end.linkTo(parent.end, 15.dp)
                bottom.linkTo(ads.top, 15.dp)
            })

        DisplayDialog(
            openDialog = viewModel.openAboutDialogState,
            closeDialog = { viewModel.handleEvent(SettingsMenuEvent.About(false))  },
            fullText = "Hello, this is Roman's application. This app was made as part of a course in android development. \n" +
                    "Blow are all the credits and sources that was used in the making of this application.\n" +
                    "\n" +
                    "To do icons created by Freepik - Flaticon\n" +
                    "Youtube guide Stevdza-San",
            hyperLinks = mutableMapOf(
                "Flaticon" to "https://www.148apps.com/app/1324125713/",
                "dcancelas" to "https://github.com/dcancelas/simon-says/blob/cff578450078bd345ca4ba6d3bd63df908e70b74/app/src/main/java/com/example/simon_says/MyViewModel.kt"
            ),
            fontSize = 18.sp,
            textStyle = MaterialTheme.typography.body1,
            title = "About TODO-list"
        )



        BannersAds(modifier = Modifier.constrainAs(ads) {
            linkTo(
                start = parent.start,
                end = parent.end
            )
            bottom.linkTo(parent.bottom)
        })

    }
}


@Composable
fun TitleText(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = modifier,
            text =
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = LightBrightBlue)) {
                    append("S")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Red)) {
                    append("I")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Yellow)) {
                    append("M")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Green)) {
                    append("O")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = LightBrightBlue)) {
                    append("N")
                }
                append(" ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Red)) {
                    append("S")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Yellow)) {
                    append("A")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Green)) {
                    append("Y")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = LightBrightBlue)) {
                    append("S")
                }
            },
            fontSize = 60.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier,
            text =
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Red)) {
                    append("G")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Yellow)) {
                    append("A")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Green)) {
                    append("M")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = LightBrightBlue)) {
                    append("E")
                }
            },
            fontSize = 60.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MenuContent(viewModel: MenuViewModel, modifier: Modifier, navigateToPanelGame: (GameMode) -> Unit) {

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

@Composable
fun SettingsButton(viewModel: MenuViewModel, modifier: Modifier) {

    val coroutineScope = rememberCoroutineScope()

    FloatingActionButtonMenu(
        modifier = modifier,
        viewModel = viewModel,
        menu = floatingActionMenuOptions(LocalContext.current) {
            coroutineScope.launch {
                viewModel.handleEvent(it)
            }
        }
    )

}





@Preview
@Composable
private fun MenuScreenPreview() {

}
