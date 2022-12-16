package com.dorin.simonsaysgame.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val GreyPrimary = Color(0xffeeeeee)
val GreyLightPrimary = Color(0xFFFFFFFF)
val GeryDarkPrimary = Color(0xFFBCBCBC)
val GreySecondary = Color(0xFF616161)
val GreyLightSecondary = Color(0xFF8e8e8e)
val GreyDarkSecondary = Color(0xFF373737)


val Black = Color(0xFF000000)
val Grey = Color(0xFFA29999)
val TextWhite = Color(0xffeeeeee)
val DeepBlue = Color(0xff06164c)
val ButtonBlue = Color(0xff505cf3)
val DarkerButtonBlue = Color(0xff566894)
val LightRed = Color(0xfffc879a)
val AquaBlue = Color(0xff9aa5c4)
val OrangeYellow1 = Color(0xfff0bd28)
val OrangeYellow2 = Color(0xfff1c746)
val OrangeYellow3 = Color(0xfff4cf65)
val OrangeYellow4 = Color(0xFFFFFF00)
val OrangeYellow5 = Color(0xFFFFFF51)
val Beige1 = Color(0xfffdbda1)
val Beige2 = Color(0xfffcaf90)
val Beige3 = Color(0xfff9a27b)
val LightGreen1 = Color(0xff54e1b6)
val LightGreen2 = Color(0xff36ddab)
val LightGreen3 = Color(0xff11d79b)
val LightGreen4 = Color(0xFF00FD00)
val BlueViolet1 = Color(0xffaeb4fd)
val BlueViolet2 = Color(0xff9fa5fe)
val BlueViolet3 = Color(0xff8f98fd)


/*** Simon Says Colors ***/
val Green = Color(0xFF00FF00)
val PressedGreen = Color(0x4F00FF00)
val Red = Color(0xFFFF0000)
val PressedRed = Color(0x5EFF0000)
val Yellow = Color(0xFFFFFF00)
val PressedYellow = Color(0x85FFFF00)
val LightBrightBlue = Color(0xFF2196F3)
val PressedLightBrightBlue = Color(0x7C2196F3)

val btnColors = listOf<Color>(Green,PressedGreen,Red,PressedRed,Yellow,PressedYellow,LightBrightBlue,PressedLightBrightBlue)


val Colors.SplashScreenBackground: Color
    @Composable
    get() = if (isLight) Black else Black



