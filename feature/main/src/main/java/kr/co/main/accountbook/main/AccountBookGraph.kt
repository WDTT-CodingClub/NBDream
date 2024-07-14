package kr.co.main.accountbook.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun AccountBookGraph(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    data: List<Float>,
    graphHeight: Int
) {
    val total = data.sum()
    val angles = data.map { it / total * 360f }

    val angleList = remember(data) { angles.map { Animatable(0f) } }

    LaunchedEffect(data) {
        angleList.forEachIndexed { index, value ->
            launch {
                value.snapTo(0f)
                value.animateTo(
                    targetValue = angles[index],
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        }
    }

    Canvas(modifier = modifier.height(graphHeight.dp)) {
        val strokeWidth = graphHeight.dp.toPx() / 4
        val radius = (graphHeight.dp.toPx() - strokeWidth) / 2
        val centerX = size.width / 2f
        val centerY = radius + strokeWidth / 2

        drawGraph(colors, radius, strokeWidth, centerX, centerY, angleList)
    }
}

private fun DrawScope.drawGraph(
    colors: List<Color>,
    radius: Float,
    strokeWidth: Float,
    centerX: Float,
    centerY: Float,
    angleList: List<Animatable<Float, AnimationVector1D>>
) {
    var startAngle = -90f

    angleList.forEachIndexed { index, value ->
        val color = colors[index]
        val rangeAngle = value.value

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = rangeAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth),
            topLeft = Offset(centerX - radius, centerY - radius),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
        )

        startAngle += rangeAngle
    }
}
