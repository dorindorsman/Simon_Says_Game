package com.dorin.simonsaysgame.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val ColorPaletteSimon = lightColors(
    background = Color.Black,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    )

private val ColorPaletteSimon2 = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    secondary = Color.Black
)

private val DarkColorPaletteSimon = darkColors(
    primary = GeryDarkPrimary,
    primaryVariant = GreySecondary,
    secondary = GreyDarkSecondary
)

private val LightColorPaletteSimon = lightColors(
    primary = GreyPrimary,
    primaryVariant = GreySecondary,
    secondary = GreyLightSecondary
)




@Composable
fun SimonSaysGameTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        ColorPaletteSimon2
    } else {
        ColorPaletteSimon2
    }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}