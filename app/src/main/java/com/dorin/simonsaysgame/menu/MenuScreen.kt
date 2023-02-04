package com.dorin.simonsaysgame.menu

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.ads.BannersAds
import com.dorin.simonsaysgame.menu.settings.FloatingActionButtonMenu
import com.dorin.simonsaysgame.menu.settings.settings_menu.floatingActionMenuOptions
import com.dorin.simonsaysgame.ui.theme.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream


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

fun openHtmlTextDialog(activity: Activity, fileNameInAssets: String?) {
    var str: String? = ""
    var `is`: InputStream? = null
    try {
        `is` = activity.assets.open(fileNameInAssets!!)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        str = String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    //val materialAlertDialogBuilder = MaterialAlertDialogBuilder(activity)
    val materialAlertDialogBuilder = AlertDialog.Builder(activity.applicationContext)
    materialAlertDialogBuilder.setPositiveButton("Close", null)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        materialAlertDialogBuilder.setMessage(Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY))
    } else {
        materialAlertDialogBuilder.setMessage(Html.fromHtml(str))
    }
    val al: AlertDialog = materialAlertDialogBuilder.show()
    val alertTextView = al.findViewById<TextView>(android.R.id.message)
    alertTextView.movementMethod = LinkMovementMethod.getInstance()
}

@Composable
fun SettingsButton(viewModel: MenuViewModel, modifier: Modifier) {

    val coroutineScope = rememberCoroutineScope()

    FloatingActionButtonMenu(
        modifier = modifier,
        viewModel = viewModel,
        menu = floatingActionMenuOptions {
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
