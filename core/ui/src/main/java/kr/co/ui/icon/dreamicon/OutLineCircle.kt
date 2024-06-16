package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

val DreamIcon.OutLineCircle: ImageVector
    get() {
        if (_OutLineCircle != null) {
            return _OutLineCircle!!
        }
        _OutLineCircle = Builder(
            name = "getOutLineCircle",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1f
            ) {
                moveTo(12.0f, 1.5f)
                arcTo(10.0f, 10.0f, 0.0f, true, true, 12.0f, 22.5f)
                arcTo(10.0f, 10.0f, 0.0f, true, true, 12.0f, 1.5f)
                close()
            }
        }
            .build()
        return _OutLineCircle!!
    }

private var _OutLineCircle: ImageVector? = null
