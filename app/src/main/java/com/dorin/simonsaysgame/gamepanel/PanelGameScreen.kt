package com.dorin.simonsaysgame.gamepanel

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dorin.simonsaysgame.R
import com.dorin.simonsaysgame.ads.BannersAds
import com.dorin.simonsaysgame.ads.RewardedAdsLoading
import com.dorin.simonsaysgame.ads.RewardedAdsShow
import com.dorin.simonsaysgame.ui.theme.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PanelGameScreen(
    navigateToMenuScreen: () -> Unit,
    viewModel: PanelGameViewModel,
) {

    //Define Needed Variables
    val context = LocalContext.current
    val viewState = viewModel.viewState
    val mediaPlayer = MediaPlayer.create(context, viewModel.btnSoundState)

    //Start Button Used To Begin Game
    SimonSaysGameBoard(context, viewState, viewModel, mediaPlayer, navigateToMenuScreen)

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimonSaysGameBoard(
    context: Context,
    viewState: PanelGameViewModel.ViewState,
    viewModel: PanelGameViewModel,
    mediaPlayer: MediaPlayer,
    navigateToMenuScreen: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(15.dp)
    ) {

        val (highScore, liveScore, btnTop, btnBottom, circle, ads) = createRefs()

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(highScore) {
                    top.linkTo(parent.top, margin = 10.dp)
                }) {
            if (viewState.gameRunning) {
                Text(
                    text = "High Score: ${viewModel.highScore}",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(liveScore) {
                    top.linkTo(highScore.bottom, margin = 10.dp)
                }) {
            if (!viewState.gameRunning) {
                StartButton(viewModel)
            } else {
                Text(
                    text = "Lives: ${viewState.attemptsLeft}", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Score: ${viewState.score}", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold
                )
            }
        }

        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(btnTop) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                )
                top.linkTo(liveScore.top, 180.dp)
            }) {


            SimonSaysButton(
                color = Green,
                pressColor = PressedGreen,
                shape = GameShapeTopLeft.small as RoundedCornerShape,
                index = 0,
                viewState = viewState,
                viewModel = viewModel,
                context = context,
                mediaPlayer = mediaPlayer
            )

            SimonSaysButton(
                color = Red,
                pressColor = PressedRed,
                shape = GameShapeTopRight.small as RoundedCornerShape,
                index = 1,
                viewState = viewState,
                viewModel = viewModel,
                context = context,
                mediaPlayer = mediaPlayer
            )

        }

        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(btnBottom) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                )
                top.linkTo(btnTop.bottom)
            }) {

            SimonSaysButton(
                color = Yellow,
                pressColor = PressedYellow,
                shape = GameShapeBottomLeft.small as RoundedCornerShape,
                index = 2,
                viewState = viewState,
                viewModel = viewModel,
                context = context,
                mediaPlayer = mediaPlayer
            )

            SimonSaysButton(
                color = LightBrightBlue,
                pressColor = PressedLightBrightBlue,
                shape = GameShapeBottomRight.small as RoundedCornerShape,
                index = 3,
                viewState = viewState,
                viewModel = viewModel,
                context = context,
                mediaPlayer = mediaPlayer
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(circle) {
                    linkTo(
                        start = parent.start, end = parent.end, top = btnTop.top, bottom = btnBottom.bottom
                    )
                }
                .size(100.dp),
            shape = RoundedCornerShape(150.dp), onClick = {},
            backgroundColor = Color.Black,
        ) {
            Text(
                text = stringResource(id = R.string.simon), fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold
            )
        }

        BannersAds(modifier = Modifier.constrainAs(ads) {
            linkTo(
                start = parent.start, end = parent.end
            )
            bottom.linkTo(parent.bottom)
        })

        if (viewState.attemptsLeft == 0) {
            RewardedAdsLoading(context, viewModel)
            if (viewModel.rewardedAdsLoadingState) {
                RewardedAdsShow(context, viewModel)
                AlertDialogScreen(context, viewState, viewModel, navigateToMenuScreen)
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertDialogScreen(
    context: Context, viewState: PanelGameViewModel.ViewState, viewModel: PanelGameViewModel, navigateToMenuScreen: () -> Unit
) {
    AlertDialog(onDismissRequest = {},
        title = { Text(stringResource(id = R.string.game_completed)) },
        text = { Text("Your Score: ${viewState.score}") },
        confirmButton = {
            ExtendedFloatingActionButton(text = {
                Text(
                    modifier = Modifier.wrapContentSize(), text = stringResource(id = R.string.retry), color = Color.White
                )
            }, shape = RoundedCornerShape(16.dp), onClick = {
                viewModel.handleEvent(PanelGameEvent.SetHighScore)
                viewModel.reset()
            })
        },
        dismissButton = {
            ExtendedFloatingActionButton(text = {
                Text(
                    modifier = Modifier.wrapContentSize(), text = stringResource(id = R.string.back_to_game_menu), color = Color.White
                )
            }, shape = RoundedCornerShape(16.dp), onClick = {
                viewModel.handleEvent(PanelGameEvent.SetHighScore)
                viewModel.reset()
                navigateToMenuScreen()
            })
        })
}

@Composable
fun StartButton(viewModel: PanelGameViewModel) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .wrapContentSize()
            .height(100.dp)
            .padding(vertical = 20.dp)
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
        onClick = {
            viewModel.startRound()
        },
        text = {
            Text(
                modifier = Modifier.wrapContentSize(), text = stringResource(id = R.string.play), style = TextStyle(
                    fontFamily = FontFamily.Monospace, fontSize = 20.sp, color = Color.White
                )
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_arrow),
                modifier = Modifier.size(30.dp),
                contentDescription = null,
                tint = Color.White
            )
        },
        shape = MaterialTheme.shapes.small,
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimonSaysButton(
    color: Color,
    pressColor: Color,
    shape: RoundedCornerShape,
    index: Int,
    viewState: PanelGameViewModel.ViewState,
    viewModel: PanelGameViewModel,
    context: Context,
    mediaPlayer: MediaPlayer
) {

    //Animation Variables
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) 0.9f else 1f)


    //Logic Variables
    val pt = viewState.playerTurn
    var btnColorState = remember { mutableStateOf(color) }

    //viewModel.handleEvent(PanelGameEvent.SetButtonColorState(color))
    //viewModel.handleEvent(PanelGameEvent.SetButtonSound(index))

    //Sounds Variable
    var buttonSound = when (color) {
        Green -> R.raw.green
        Red -> R.raw.red
        Yellow -> R.raw.yellow
        LightBrightBlue -> R.raw.blue
        else -> {
            R.raw.error
        }
    }

    if (viewState.btnStates[index] == 1) {
        btnColorState = remember { mutableStateOf(pressColor) }
    } else if (viewState.btnStates[index] == 2) {
        btnColorState = remember { mutableStateOf(correct) }
        buttonSound = R.raw.victory
    } else if (viewState.btnStates[index] == 3) {
        btnColorState = remember { mutableStateOf(inCorrect) }
        buttonSound = R.raw.error
    }

    //mediaPlayer.playbackParams.setAudioFallbackMode(buttonSound)
    val mediaPlayer = MediaPlayer.create(context, buttonSound)

    Button(onClick = {}, shape = shape, modifier = Modifier
        .padding(5.dp)
        .size(150.dp)
        .scale(scale.value)
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (pt) {
                        selected.value = true
                        btnColorState.value = pressColor
                        //viewModel.handleEvent(PanelGameEvent.SetButtonSound(index))
                        //viewModel.handleEvent(PanelGameEvent.SetButtonColorState(pressColor))
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (pt) {
                        selected.value = false
                        btnColorState.value = color
                        viewModel.receiveInput(index)
                        //viewModel.handleEvent(PanelGameEvent.SetButtonSound(index))
                        //viewModel.handleEvent(PanelGameEvent.SetButtonColorState(color))
                    }
                }
            }
            true
        }, colors = ButtonDefaults.buttonColors(containerColor = btnColorState.value)
    ) {
        if (btnColorState.value == pressColor || viewState.btnStates[index] == 2 || viewState.btnStates[index] == 3) {
            mediaPlayer.start()
        }
    }

}


//@RequiresApi(Build.VERSION_CODES.O)
//fun saveScore(score: Int, context: Context) {
//    ScoreImpl.addScore(
//        gameId = 1, gameScore = score, context = context
//    )
//}

@Preview
@Composable
private fun MenuScreenPreview() {

}
