package kr.co.main.accountbook.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
internal fun AccountBookGraph(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    data: List<Float>,
    graphHeight: Int
) {
    val total = data.sum()
    val angles = data.map { it / total * 360f }

    Canvas(modifier = modifier.height(graphHeight.dp)) {
        val strokeWidth = graphHeight.dp.toPx() / 4
        val radius = (graphHeight.dp.toPx() - strokeWidth) / 2
        val centerX = size.width / 2f
        val centerY = radius + strokeWidth / 2

        drawGraph(angles, colors, radius, strokeWidth, centerX, centerY)
    }
}

private fun DrawScope.drawGraph(
    angles: List<Float>,
    colors: List<Color>,
    radius: Float,
    strokeWidth: Float,
    centerX: Float,
    centerY: Float
) {
    var startAngle = -90f

    angles.forEachIndexed { index, angle ->
        val color = colors[index]

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = angle,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            topLeft = Offset(centerX - radius, centerY - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
        )

        startAngle += angle
    }
}
