package com.dorin.simonsaysgame.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.dorin.simonsaysgame.ui.theme.Green
import com.dorin.simonsaysgame.ui.theme.LightBrightBlue
import com.dorin.simonsaysgame.ui.theme.Red
import com.dorin.simonsaysgame.ui.theme.Yellow

@Composable
fun TitleText(modifier: Modifier, fontSize: TextUnit) {
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
            fontSize = fontSize,
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
            fontSize = fontSize,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}