package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Spinner: ImageVector
    get() {
        if (_spinner != null) {
            return _spinner!!
        }
        _spinner = Builder(name = "Spinner", defaultWidth = 10.0.dp, defaultHeight = 6.0.dp,
                viewportWidth = 10.0f, viewportHeight = 6.0f).apply {
            path(fill = SolidColor(Color(0xFF121212)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.6047f, 5.3017f)
                curveTo(5.2857f, 5.6701f, 4.7143f, 5.6701f, 4.3953f, 5.3017f)
                lineTo(0.9502f, 1.3237f)
                curveTo(0.5015f, 0.8056f, 0.8696f, -0.0f, 1.555f, -0.0f)
                lineTo(8.445f, -0.0f)
                curveTo(9.1304f, -0.0f, 9.4985f, 0.8056f, 9.0498f, 1.3237f)
                lineTo(5.6047f, 5.3017f)
                close()
            }
        }
        .build()
        return _spinner!!
    }

private var _spinner: ImageVector? = null
