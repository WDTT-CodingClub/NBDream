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

public val DreamIcon.MoreVertical: ImageVector
    get() {
        if (_morevertical != null) {
            return _morevertical!!
        }
        _morevertical = Builder(name = "MoreVertical", defaultWidth = 23.0.dp, defaultHeight =
                19.0.dp, viewportWidth = 23.0f, viewportHeight = 19.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9E9E9E)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 5.0f)
                curveTo(11.7761f, 5.0f, 12.0f, 4.7761f, 12.0f, 4.5f)
                curveTo(12.0f, 4.2239f, 11.7761f, 4.0f, 11.5f, 4.0f)
                curveTo(11.2239f, 4.0f, 11.0f, 4.2239f, 11.0f, 4.5f)
                curveTo(11.0f, 4.7761f, 11.2239f, 5.0f, 11.5f, 5.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9E9E9E)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 10.0f)
                curveTo(11.7761f, 10.0f, 12.0f, 9.7761f, 12.0f, 9.5f)
                curveTo(12.0f, 9.2239f, 11.7761f, 9.0f, 11.5f, 9.0f)
                curveTo(11.2239f, 9.0f, 11.0f, 9.2239f, 11.0f, 9.5f)
                curveTo(11.0f, 9.7761f, 11.2239f, 10.0f, 11.5f, 10.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9E9E9E)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.5f, 15.0f)
                curveTo(11.7761f, 15.0f, 12.0f, 14.7761f, 12.0f, 14.5f)
                curveTo(12.0f, 14.2239f, 11.7761f, 14.0f, 11.5f, 14.0f)
                curveTo(11.2239f, 14.0f, 11.0f, 14.2239f, 11.0f, 14.5f)
                curveTo(11.0f, 14.7761f, 11.2239f, 15.0f, 11.5f, 15.0f)
                close()
            }
        }
        .build()
        return _morevertical!!
    }

private var _morevertical: ImageVector? = null
