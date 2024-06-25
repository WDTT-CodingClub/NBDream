package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Dots: ImageVector
    get() {
        if (_dots != null) {
            return _dots!!
        }
        _dots = Builder(
            name = "Dots 1",
            defaultWidth = 22.dp,
            defaultHeight = 22.dp,
            viewportWidth = 22f,
            viewportHeight = 22f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF594D42)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(2.75f, 13.0625f)
                curveTo(3.8891f, 13.0625f, 4.8125f, 12.1391f, 4.8125f, 11f)
                curveTo(4.8125f, 9.8609f, 3.8891f, 8.9375f, 2.75f, 8.9375f)
                curveTo(1.6109f, 8.9375f, 0.6875f, 9.8609f, 0.6875f, 11f)
                curveTo(0.6875f, 12.1391f, 1.6109f, 13.0625f, 2.75f, 13.0625f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF594D42)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(11f, 13.0625f)
                curveTo(12.1391f, 13.0625f, 13.0625f, 12.1391f, 13.0625f, 11f)
                curveTo(13.0625f, 9.8609f, 12.1391f, 8.9375f, 11f, 8.9375f)
                curveTo(9.8609f, 8.9375f, 8.9375f, 9.8609f, 8.9375f, 11f)
                curveTo(8.9375f, 12.1391f, 9.8609f, 13.0625f, 11f, 13.0625f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF594D42)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(19.25f, 13.0625f)
                curveTo(20.3891f, 13.0625f, 21.3125f, 12.1391f, 21.3125f, 11f)
                curveTo(21.3125f, 9.8609f, 20.3891f, 8.9375f, 19.25f, 8.9375f)
                curveTo(18.1109f, 8.9375f, 17.1875f, 9.8609f, 17.1875f, 11f)
                curveTo(17.1875f, 12.1391f, 18.1109f, 13.0625f, 19.25f, 13.0625f)
                close()
            }
        }
            .build()
        return _dots!!
    }


private var _dots: ImageVector? = null