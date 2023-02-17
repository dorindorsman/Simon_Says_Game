package com.dorin.simonsaysgame

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun PurchaseMenu(onHide: () -> Unit, noAds: () -> Unit, onProgram1: () -> Unit, onProgram2: () -> Unit, onProgram3: () -> Unit) {

    Popup(alignment = Alignment.Center,onDismissRequest = { onHide() }, properties = PopupProperties(dismissOnBackPress = true)) {
        Column(
            modifier = Modifier.clip(RoundedCornerShape(50.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.purchase_title),
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .padding(20.dp),
                style = TextStyle(fontSize = 25.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Row(modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White)
                .clickable {
                    noAds()
                    onHide()
                }
                .padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    tint = Color.Black,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.purchase_no_ads),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

            Row(modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White)
                .clickable {
                    onProgram1()
                    onHide()
                }
                .padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    tint = Color.Black,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.purchase_p1),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

            Row(modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White)
                .clickable {
                    onProgram2()
                    onHide()
                }
                .padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    tint = Color.Black,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.purchase_p2),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

            Row(modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White)
                .clickable {
                    onProgram3()
                    onHide()
                }
                .padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    tint = Color.Black,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.purchase_p3),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }

            Row(modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White)
                .padding(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.purchase_little),
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 15.sp, color = Color.Black)
                )
            }


        }
    }

}