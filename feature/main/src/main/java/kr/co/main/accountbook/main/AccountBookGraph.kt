package kr.co.main.accountbook.main

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kr.co.main.accountbook.model.getDisplay
import kr.co.ui.theme.colors

@Composable
internal fun AccountBookGraph(
    data: List<AccountBookViewModel.State.PercentCategory>,
    modifier: Modifier = Modifier,
    graphHeight: Int
) {
    val total = data.sumOf { it.percent.toInt() }
    val angleInterval = if (data.size > 1) 1f else 0f
    val angles = data.map { it.percent / total * (360f - data.size * angleInterval) }
    val colors = getColorList(data.size)
    val density = LocalDensity.current.density
    val categoryColor = MaterialTheme.colors.gray2
    val percentageColor = MaterialTheme.colors.gray5

    Canvas(modifier = modifier.height(graphHeight.dp)) {
        val strokeWidth = graphHeight.dp.toPx() / 4
        val radius = (graphHeight.dp.toPx() - strokeWidth) / 2
        val centerX = radius + strokeWidth / 2
        val centerY = radius + strokeWidth / 2

        drawGraph(angles, colors, angleInterval, radius, strokeWidth, centerX, centerY)
        drawCategoryText(data, colors, total, density, categoryColor, percentageColor, radius, strokeWidth, centerY)
    }
}

@Composable
private fun getColorList(size: Int): List<Color> {
    val predefinedColors = listOf(
        MaterialTheme.colors.graph1,
        MaterialTheme.colors.graph2,
        MaterialTheme.colors.graph3,
        MaterialTheme.colors.graph4,
        MaterialTheme.colors.graph5,
        MaterialTheme.colors.graph6,
        MaterialTheme.colors.graph7,
        MaterialTheme.colors.graph8,
        MaterialTheme.colors.graph9,
        MaterialTheme.colors.graph10,
        MaterialTheme.colors.graph11
    )
    return List(size) { index -> predefinedColors[index % predefinedColors.size] }
}

private fun DrawScope.drawGraph(
    angles: List<Float>,
    colors: List<Color>,
    angleInterval: Float,
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

        startAngle += angle + angleInterval
    }
}

private fun DrawScope.drawCategoryText(
    data: List<AccountBookViewModel.State.PercentCategory>,
    colors: List<Color>,
    total: Int,
    density: Float,
    categoryColor: Color,
    percentageColor: Color,
    radius: Float,
    strokeWidth: Float,
    centerY: Float
) {
    val textSizePx = 16f * density
    val circleRadiusPx = 4.dp.toPx()

    val graphRightX = radius * 2 + strokeWidth
    val outerPadding = 16.dp.toPx()
    val textStartX = graphRightX + outerPadding

    val textStartY = centerY - ((data.size - 1) * (textSizePx + 4.dp.toPx()) / 2)
    val verticalInterval = 4.dp.toPx()
    val circleTextGap = 8.dp.toPx()
    val textPercentageGap = 8.dp.toPx()

    data.forEachIndexed { index, item ->
        val textY = textStartY + index * (textSizePx + verticalInterval)
        val percentage = ((item.percent / total) * 100).toInt()

        drawCircle(
            color = colors[index],
            center = Offset(textStartX, textY - 5),
            radius = circleRadiusPx
        )

        val categoryTextX = textStartX + circleTextGap
        val percentageTextX = categoryTextX + Paint().apply { textSize = textSizePx }.measureText(item.category.getDisplay()) + textPercentageGap

        drawIntoCanvas {
            it.nativeCanvas.drawText(
                item.category.getDisplay(),
                categoryTextX,
                textY,
                Paint().apply {
                    color = categoryColor.toArgb()
                    textSize = textSizePx
                    textAlign = Paint.Align.LEFT
                }
            )

            it.nativeCanvas.drawText(
                "$percentage%",
                percentageTextX,
                textY,
                Paint().apply {
                    color = percentageColor.toArgb()
                    textSize = textSizePx
                    textAlign = Paint.Align.LEFT
                }
            )
        }
    }
}
