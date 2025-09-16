package com.example.padm.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.padm.ui.theme.PirateColors

@Composable
fun RopeDivider(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .padding(vertical = 2.dp)
    ) {
        val y = size.height / 2f
        drawLine(
            color = PirateColors.Rope,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(22f, 16f), 0f)
        )
    }
}
