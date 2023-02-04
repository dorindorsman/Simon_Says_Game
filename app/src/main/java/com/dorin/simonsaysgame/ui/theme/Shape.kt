package com.dorin.simonsaysgame.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)


val GameShapeTopLeft = Shapes(
    small = RoundedCornerShape(topStart = 150.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
    medium = RoundedCornerShape(topStart = 100.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
)

val GameShapeTopRight = Shapes(
    small = RoundedCornerShape(topStart = 0.dp, topEnd = 150.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
)

val GameShapeBottomLeft = Shapes(
    small = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 150.dp, bottomEnd = 0.dp)
)

val GameShapeBottomRight = Shapes(
    small = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 150.dp)
)

