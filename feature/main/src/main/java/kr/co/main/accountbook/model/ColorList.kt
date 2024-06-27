package kr.co.main.accountbook.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kr.co.ui.theme.colors

@Composable
internal fun Int.getColorList(): List<Color> {
    val predefinedColors = listOf(
        MaterialTheme.colors.graph1,
        MaterialTheme.colors.graph3,
        MaterialTheme.colors.graph4,
        MaterialTheme.colors.graph5,
    )
    val grayColor = MaterialTheme.colors.gray7

    return if (this <= predefinedColors.size) {
        List(this) { index -> predefinedColors[index % predefinedColors.size] }
    } else {
        val colorList = mutableListOf<Color>()
        for (i in 0 until this) {
            if (i < predefinedColors.size) {
                colorList.add(predefinedColors[i % predefinedColors.size])
            } else {
                colorList.add(grayColor)
            }
        }
        colorList
    }
}

internal const val MAX_CATEGORY_COUNT = 4