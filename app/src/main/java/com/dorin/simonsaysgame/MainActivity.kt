package com.dorin.simonsaysgame

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.dorin.simonsaysgame.datastore.DataStoreRepository
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
    private var menuType = MenuType.PREMIUM

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var menuState by remember {
                mutableStateOf(MenuState.HIDE)
            }
            SimonSaysGameTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
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
                        DrawerBody(items = ListMenuOptions(menuViewModel),
                            onItemClick = {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                    when (it.id) {
                                        MenuAction.PREMIUM -> {
                                            menuType = MenuType.PREMIUM
                                            menuState = MenuState.SHOW
                                        }
                                        MenuAction.PURCHASE -> {
                                            menuType = MenuType.PURCHASE
                                            menuState = MenuState.SHOW
                                        }
                                        MenuAction.UNPREMIUM -> {
                                            menuType = MenuType.UNPREMIUM
                                            menuState = MenuState.SHOW
                                        }
                                        else -> {
                                            getOnItemClick(it.id)
                                        }
                                    }
                                }
                            }
                        )
                    },
                    drawerBackgroundColor = Color.Black,
                    drawerContentColor = Color.White,
                ) {


                    if (menuState == MenuState.SHOW) {
                        Log.d("dorin1", menuViewModel.userType.name)
                        Log.d("dorin2", menuType.name)
                        when (menuType) {
                                    MenuType.PREMIUM -> PremiumMenu({ menuState = MenuState.HIDE }, {
                                scope.launch {
                                    DataStoreRepository(context).persistUserTypeState(UserType.PREMIUM.ordinal)
                                }
                            })
                            MenuType.PURCHASE -> PurchaseMenu ({ menuState = MenuState.HIDE },
                             {
                                scope.launch {
                                    DataStoreRepository(context).persistUserAdsState(PurchaseState.NO_ADS.ordinal)
                                    Toast.makeText(context, "You made a one-time purchase: NO ADS PROGRAM",Toast.LENGTH_SHORT).show()
                                }
                            },
                                {
                                    scope.launch {
                                        DataStoreRepository(context).persistUserPurchaseState(PurchaseState.PROGRAM1.ordinal, panelGameViewModel.coins)
                                        Toast.makeText(context, "You made purchase: 20 Coins",Toast.LENGTH_SHORT).show()
                                    }
                                },
                                {
                                    scope.launch {
                                        DataStoreRepository(context).persistUserPurchaseState(PurchaseState.PROGRAM2.ordinal, panelGameViewModel.coins)
                                        Toast.makeText(context, "You made purchase: 40 Coins",Toast.LENGTH_SHORT).show()
                                    }
                                },
                                {
                                    scope.launch {
                                        DataStoreRepository(context).persistUserPurchaseState(PurchaseState.PROGRAM3.ordinal, panelGameViewModel.coins)
                                        Toast.makeText(context, "You made purchase: 60 Coins",Toast.LENGTH_SHORT).show()
                                    }
                                }

                            )
                            MenuType.UNPREMIUM -> UnPremiumMenu({ menuState = MenuState.HIDE }, {
                                scope.launch {
                                    DataStoreRepository(context).persistUserTypeState(UserType.NORMAL.ordinal)
                                }
                            })
                        }
                    }

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

    private fun getOnItemClick(action: MenuAction) {
        when (action) {
            MenuAction.ABOUT -> {
                HtmlHelper.openDialog(this, "about.html")
            }
            MenuAction.TERMS -> {
                HtmlHelper.openDialog(this, "terms_of_use.html")
            }
            MenuAction.PRIVACY -> {
                HtmlHelper.openDialog(this, "privacy_policy.html")
            }
            MenuAction.PURCHASE -> {
                //persistUserPurchaseState(PurchaseState.YES)
            }
            else->{}
        }
    }
}


enum class MenuAction {
    ABOUT, TERMS, PRIVACY, PREMIUM, PURCHASE, UNPREMIUM
}

enum class UserType {
    NORMAL, PREMIUM
}

enum class MenuType {
    PURCHASE, PREMIUM, UNPREMIUM
}

enum class PurchaseState {
    NO_ADS, PROGRAM1, PROGRAM2, PROGRAM3
}

enum class MenuState {
    HIDE, SHOW
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimonSaysGameTheme {

    }
}