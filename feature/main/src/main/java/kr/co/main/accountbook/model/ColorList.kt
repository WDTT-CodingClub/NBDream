package kr.co.main.accountbook.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kr.co.ui.theme.colors

@Composable
internal fun Int.getColorList(): List<Color> {
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
    return List(this) { index -> predefinedColors[index % predefinedColors.size] }
}

internal const val MAX_CATEGORY_COUNT = 2