package kr.co.wdtt.nbdream.ui.accountbook

import android.graphics.Paint
import androidx.compose.foundation.Canvas
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
import kotlin.random.Random

fun getColorList(size: Int): List<Color> {
    return List(size) {
        Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1.0f
        )
    }
}

@Composable
fun GraphView(
    data: List<Float>,
    categories: List<String>,
    label: String,
    modifier: Modifier = Modifier
) {
    val total = data.sum().toInt()
    val angleInterval = 1f
    val angles = data.map { it / total * (360f - data.size * angleInterval) }
    val colors = getColorList(data.size)

    Canvas(modifier = modifier) {
        drawGraph(angles, colors, data, total, angleInterval)
        drawCategoryText(categories, colors)
        drawCenterText(label)
    }
}

fun DrawScope.drawGraph(
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

fun DrawScope.drawPercentageText(
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

fun DrawScope.drawCategoryText(categories: List<String>, colors: List<Color>) {
    val maxTextWidth = categories.maxByOrNull { it.length }?.let {
        Paint().apply { textSize = 20f }.measureText(it)
    } ?: 0f
    val textX = size.width + maxTextWidth / 2 + 50
    val textStartY = size.height / 2 - (categories.size * 20f) / 2
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

fun DrawScope.drawCenterText(label: String) {
    drawIntoCanvas {
        it.nativeCanvas.drawText(
            label,
            size.width / 2,
            size.height / 2,
            Paint().apply {
                color = Color.Black.toArgb()
                textSize = 32f
                textAlign = Paint.Align.CENTER
            }
        )
    }
}
