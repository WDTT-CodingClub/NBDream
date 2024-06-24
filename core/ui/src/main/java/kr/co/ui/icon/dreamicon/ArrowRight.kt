package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Arrowright: ImageVector
    get() {
        if (_arrowright != null) {
            return _arrowright!!
        }
        _arrowright = Builder(name = "Arrowright", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9E9E9E)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.5f, 15.0f)
                lineTo(12.5f, 10.0f)
                lineTo(7.5f, 5.0f)
            }
        }
        .build()
        return _arrowright!!
    }

private var _arrowright: ImageVector? = null
