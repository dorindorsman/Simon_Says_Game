package com.dorin.simonsaysgame.gamePanel

import android.content.Context
import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
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
import com.dorin.simonsaysgame.score.ScoreImpl
import com.dorin.simonsaysgame.ui.theme.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PanelGameScreen(
    navigateToMenuScreen: () -> Unit,
    viewModel: PanelGameScreenViewModel,
) {

    //Define Needed Variables

    val viewState = viewModel.viewState

    //Start Button Used To Begin Game
    SimonSaysGameBoard(viewState, viewModel, navigateToMenuScreen)


    //navigateToPanelGame()

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimonSaysGameBoard(
    viewState: PanelGameScreenViewModel.ViewState,
    viewModel: PanelGameScreenViewModel,
    navigateToMenuScreen: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(15.dp)
    ) {

        val (liveScore, btnTop, btnBottom, circle, ads) = createRefs()

        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(liveScore) {
                    top.linkTo(parent.top, margin = 20.dp)
                }) {
            if (!viewState.gameRunning) {
                StartButton(viewModel)
            } else {
                Text(
                    text = "Chances: ${viewState.attemptsLeft}",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(120.dp))
                Text(
                    text = "Scores: ${viewState.score}",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
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
                color = Green, pressColor = PressedGreen,
                shape = GameShapeTopLeft.small as RoundedCornerShape,
                index = 0,
                viewState = viewState,
                viewModel = viewModel
            )

            SimonSaysButton(
                color = Red, pressColor = PressedRed,
                shape = GameShapeTopRight.small as RoundedCornerShape,
                index = 1,
                viewState = viewState,
                viewModel = viewModel
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
            })
        {

            SimonSaysButton(
                color = Yellow, pressColor = PressedYellow,
                shape = GameShapeBottomLeft.small as RoundedCornerShape,
                index = 2,
                viewState = viewState,
                viewModel = viewModel
            )

            SimonSaysButton(
                color = LightBrightBlue, pressColor = PressedLightBrightBlue,
                shape = GameShapeBottomRight.small as RoundedCornerShape,
                index = 3,
                viewState = viewState,
                viewModel = viewModel
            )
        }

        FloatingActionButton(modifier = Modifier
            .wrapContentSize()
            .constrainAs(circle) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = btnTop.top,
                    bottom = btnBottom.bottom
                )
            }
            .size(100.dp),
            shape = RoundedCornerShape(150.dp), onClick = {}, content = {},
            backgroundColor = Color.Black
        )

        BannersAds(modifier = Modifier.constrainAs(ads) {
            linkTo(
                start = parent.start,
                end = parent.end
            )
            bottom.linkTo(parent.bottom)
        })


        if (viewState.attemptsLeft == 0) {
            RewardedAdsLoading(LocalContext.current, viewModel)
            if (viewModel.rewardedAdsLoadingState) {
                RewardedAdsShow(LocalContext.current, viewModel)
                AlertDialogScreen(viewState, viewModel, navigateToMenuScreen)
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertDialogScreen(
    viewState: PanelGameScreenViewModel.ViewState,
    viewModel: PanelGameScreenViewModel,
    navigateToMenuScreen: () -> Unit
) {
    val context = LocalContext.current
    AlertDialog(onDismissRequest = {},
        title = { Text(stringResource(id = R.string.game_completed)) },
        text = { Text("Your Score: ${viewState.score}") },
        confirmButton = {
            ExtendedFloatingActionButton(text = {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.retry),
                    color = Color.White
                )
            },
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    saveScore(viewState.score, context)
                    viewModel.reset()
                })
        },
        dismissButton = {
            ExtendedFloatingActionButton(text = {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.back_to_game_menu),
                    color = Color.White
                )
            },
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    saveScore(viewState.score, context)
                    navigateToMenuScreen()
                })
        }
    )
}

@Composable
fun StartButton(viewModel: PanelGameScreenViewModel) {
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
                modifier = Modifier.wrapContentSize(),
                text = stringResource(id = R.string.play),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_arrow),
                modifier = Modifier
                    .size(30.dp),
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
    viewState: PanelGameScreenViewModel.ViewState,
    viewModel: PanelGameScreenViewModel
) {
    val pt = viewState.playerTurn
    var btnColorState = remember { mutableStateOf(color) }

    if (viewState.btnStates[index] == 1) {
        btnColorState = remember { mutableStateOf(pressColor) }
    } else if (viewState.btnStates[index] == 2) {
        btnColorState = remember { mutableStateOf(Red) }
    }


    Button(
        onClick = {},
        shape = shape,
        modifier = Modifier
            .padding(5.dp)
            .size(150.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (pt) {
                            btnColorState.value = pressColor
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (pt) {
                            btnColorState.value = color
                            viewModel.receiveInput(index)
                        }
                    }
                }
                true
            },
        colors = ButtonDefaults.buttonColors(containerColor = btnColorState.value)
    ) {

    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun saveScore(score: Int, context: Context) {
    ScoreImpl.addScore(
        gameId = 1,
        gameScore = score, context = context
    )

}

@Preview
@Composable
private fun MenuScreenPreview() {

}


//top
//            FloatingActionButton(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .size(150.dp),
//                shape = GameShapeTopLeft.small,
//                backgroundColor = Green,
//                onClick = {
//
//                },
//                content = {}
//            )

//            FloatingActionButton(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .size(150.dp),
//                shape = GameShapeTopRight.small,
//                backgroundColor = Red,
//                onClick = {
//
//                },
//                content = {}
//            )


//bottom
//            FloatingActionButton(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .size(150.dp),
//                shape = GameShapeBottomLeft.small,
//                backgroundColor = Yellow,
//                onClick = {
//
//                },
//                content = {}
//            )
//
//            FloatingActionButton(
//                modifier = Modifier
//                    .padding(5.dp)
//                    .size(150.dp),
//                shape = GameShapeBottomRight.small,
//                backgroundColor = LightBrightBlue,
//                onClick = {
//
//                },
//                content = {}
//            )
