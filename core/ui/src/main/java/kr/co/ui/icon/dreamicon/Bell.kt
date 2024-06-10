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

public val DreamIcon.Bell: ImageVector
    get() {
        if (_bell != null) {
            return _bell!!
        }
        _bell = Builder(name = "Bell", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(18.0f, 8.0f)
                curveTo(18.0f, 6.4087f, 17.3679f, 4.8826f, 16.2426f, 3.7574f)
                curveTo(15.1174f, 2.6321f, 13.5913f, 2.0f, 12.0f, 2.0f)
                curveTo(10.4087f, 2.0f, 8.8826f, 2.6321f, 7.7574f, 3.7574f)
                curveTo(6.6321f, 4.8826f, 6.0f, 6.4087f, 6.0f, 8.0f)
                curveTo(6.0f, 15.0f, 3.0f, 17.0f, 3.0f, 17.0f)
                horizontalLineTo(21.0f)
                curveTo(21.0f, 17.0f, 18.0f, 15.0f, 18.0f, 8.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(13.73f, 21.0f)
                curveTo(13.5542f, 21.3031f, 13.3019f, 21.5547f, 12.9982f, 21.7295f)
                curveTo(12.6946f, 21.9044f, 12.3504f, 21.9965f, 12.0f, 21.9965f)
                curveTo(11.6496f, 21.9965f, 11.3054f, 21.9044f, 11.0018f, 21.7295f)
                curveTo(10.6982f, 21.5547f, 10.4458f, 21.3031f, 10.27f, 21.0f)
            }
        }
        .build()
        return _bell!!
    }

private var _bell: ImageVector? = null
