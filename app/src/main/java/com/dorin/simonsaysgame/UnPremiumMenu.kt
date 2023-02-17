package com.dorin.simonsaysgame

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun UnPremiumMenu(onHide: () -> Unit, onClick: () -> Unit) {
    AlertDialog(onDismissRequest = {
        onHide()
    },
        title = { Text(stringResource(id = R.string.unpremium_title)) },
        text = {
                Text(stringResource(id = R.string.unpremium_text))
        },
        confirmButton = {
            ExtendedFloatingActionButton(text = {
                Text(
                    modifier = Modifier.wrapContentSize(), text = stringResource(id = R.string.unpremium), color = Color.White
                )
            }, shape = RoundedCornerShape(16.dp), onClick = {
                onClick()
                onHide()
            })
        },
        dismissButton = {
            ExtendedFloatingActionButton(text = {
                Text(
                    modifier = Modifier.wrapContentSize(), text = stringResource(id = R.string.no), color = Color.White
                )
            }, shape = RoundedCornerShape(16.dp), onClick = {
                onHide()
            })
        })
}