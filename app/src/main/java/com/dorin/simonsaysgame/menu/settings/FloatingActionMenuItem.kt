package com.dorin.simonsaysgame.menu.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dorin.simonsaysgame.R


@Composable
fun FloatingActionMenuItem(floatingActionMenuModel: FloatingActionMenuModel) {
    val titleStr = stringResource(id = floatingActionMenuModel.title)
    Row(
        modifier = Modifier
            .testTag(floatingActionMenuModel.id)
            .widthIn(min = 164.dp)
            .heightIn(min = 48.dp)
            .clickable(
                onClick = floatingActionMenuModel.onItemClick,
                enabled = true
            )
            .semantics(mergeDescendants = true) {
                this.text = AnnotatedString(titleStr)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .padding(
                    start = 12.dp,
                    top = 12.dp,
                    bottom = 12.dp,
                    end = 12.dp
                ),
            painter = painterResource(id = floatingActionMenuModel.icon),
            contentDescription = titleStr,
            tint = Color.Gray
        )
        Text(
            modifier = Modifier.padding(
                top = 12.dp,
                bottom = 12.dp,
                end = 12.dp
            ),
            text = titleStr,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}


@Preview(showBackground = true)
@Composable
fun FloatingActionMenuItemPreview() {
    FloatingActionMenuModel(
        icon = R.drawable.ic_privacy_policy,
        title = R.string.privacy_policy,
        onItemClick = { },
        id = "btn_privacy"
    )
}