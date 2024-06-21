package kr.co.main.accountbook.main

import android.graphics.Paint
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.colors

@Composable
internal fun AccountBookGraph(
    data: List<Float>,
    categories: List<String>,
    modifier: Modifier = Modifier
) {
    val total = data.sum().toInt()
    val angleInterval = if (data.size > 1) 1f else 0f
    val angles = data.map { it / total * (360f - data.size * angleInterval) }
    val colors = getColorList(data.size)

    Canvas(modifier = modifier) {
        drawGraph(angles, colors, data, total, angleInterval)
        drawCategoryText(categories, colors)
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
    data: List<Float>,
    total: Int,
    angleInterval: Float
) {
    var startAngle = -90f
    val strokeWidth = size.minDimension / 4

    angles.forEachIndexed { index, angle ->
        val color = colors[index]
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = angle,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )

        drawPercentageText(data[index], total, startAngle, angle)
        startAngle += angle + angleInterval
    }
}

private fun DrawScope.drawPercentageText(
    value: Float,
    total: Int,
    startAngle: Float,
    angle: Float
) {
    val midAngle = startAngle + angle / 2
    val percentage = ((value / total) * 100).toInt()
    val textX = size.width / 2 + (size.minDimension / 2) * kotlin.math.cos(Math.toRadians(midAngle.toDouble())).toFloat()
    val textY = size.height / 2 + (size.minDimension / 2) * kotlin.math.sin(Math.toRadians(midAngle.toDouble())).toFloat()

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            "$percentage%",
            textX,
            textY,
            Paint().apply {
                color = Color.Black.toArgb()
                textSize = 24f
                textAlign = Paint.Align.CENTER
            }
        )
    }
}

private fun DrawScope.drawCategoryText(categories: List<String>, colors: List<Color>) {
    val maxTextWidth = categories.maxByOrNull { it.length }?.let {
        Paint().apply { textSize = 20f }.measureText(it)
    } ?: 0f
    val textX = size.width + maxTextWidth / 2 + 50

    val graphTopY = size.height / 2 - size.minDimension / 2
    val textStartY = graphTopY + 20.dp.toPx() / 2
    val verticalInterval = 4.dp.toPx()
    categories.forEachIndexed { index, category ->
        val textY = textStartY + index * (20f + verticalInterval)

        drawCircle(
            color = colors[index],
            center = Offset(size.width + 25.dp.toPx(), textY - 5),
            radius = 4.dp.toPx()
        )

        drawIntoCanvas {
            it.nativeCanvas.drawText(
                category,
                textX,
                textY,
                Paint().apply {
                    color = Color.Black.toArgb()
                    textSize = 20f
                    textAlign = Paint.Align.LEFT
                }
            )
        }
    }
}