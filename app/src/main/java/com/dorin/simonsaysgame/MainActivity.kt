package com.dorin.simonsaysgame

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dorin.simonsaysgame.gamepanel.PanelGameViewModel
import com.dorin.simonsaysgame.menu.MenuViewModel
import com.dorin.simonsaysgame.navigationdrawer.AppBar
import com.dorin.simonsaysgame.navigationdrawer.DrawerBody
import com.dorin.simonsaysgame.navigationdrawer.DrawerHeader
import com.dorin.simonsaysgame.navigationdrawer.ListMenuOptions
import com.dorin.simonsaysgame.ui.theme.SimonSaysGameTheme
import com.dorin.simonsaysgame.utils.HtmlHelper
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val menuViewModel: MenuViewModel by viewModels()
    private val panelGameViewModel: PanelGameViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonSaysGameTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
                    },
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(items = ListMenuOptions(),
                            onItemClick = {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                    getOnItemClick(it.id)
                                }
                            }
                        )
                    },
                    drawerBackgroundColor = Color.Black,
                    drawerContentColor = Color.White,
                ) {
                    navController = rememberAnimatedNavController()
                    Navigation(
                        navController = navController,
                        menuViewModel = menuViewModel,
                        panelGameViewModel = panelGameViewModel
                    )
                }

            }
        }
    }

    private fun getOnItemClick(action: MenuAction){
        when (action) {
            MenuAction.ABOUT ->
                HtmlHelper.openDialog( this, "about.html")
            MenuAction.TERMS ->
                HtmlHelper.openDialog(this, "terms_of_use.html")
            MenuAction.PRIVACY ->
                HtmlHelper.openDialog(this, "privacy_policy.html")
            MenuAction.PREMIUM ->
            MenuAction.PURCHASE ->

        }
    }
}


enum class MenuAction {
    ABOUT, TERMS, PRIVACY, PREMIUM, PURCHASE
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimonSaysGameTheme {

    }
}