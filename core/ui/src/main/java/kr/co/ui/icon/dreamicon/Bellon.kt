package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Bellon: ImageVector
    get() {
        if (_bellon != null) {
            return _bellon!!
        }
        _bellon = Builder(name = "Bellon", defaultWidth = 33.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 33.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.5f, 12.0017f)
                curveTo(22.5f, 10.4104f, 21.8679f, 8.8843f, 20.7426f, 7.7591f)
                curveTo(19.6174f, 6.6339f, 18.0913f, 6.0017f, 16.5f, 6.0017f)
                curveTo(14.9087f, 6.0017f, 13.3826f, 6.6339f, 12.2574f, 7.7591f)
                curveTo(11.1321f, 8.8843f, 10.5f, 10.4104f, 10.5f, 12.0017f)
                curveTo(10.5f, 19.0017f, 7.5f, 21.0017f, 7.5f, 21.0017f)
                horizontalLineTo(25.5f)
                curveTo(25.5f, 21.0017f, 22.5f, 19.0017f, 22.5f, 12.0017f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(18.2295f, 25.0017f)
                curveTo(18.0537f, 25.3048f, 17.8014f, 25.5564f, 17.4978f, 25.7312f)
                curveTo(17.1941f, 25.9061f, 16.8499f, 25.9982f, 16.4995f, 25.9982f)
                curveTo(16.1492f, 25.9982f, 15.8049f, 25.9061f, 15.5013f, 25.7312f)
                curveTo(15.1977f, 25.5564f, 14.9453f, 25.3048f, 14.7695f, 25.0017f)
            }
            path(fill = SolidColor(Color(0xFFFF6666)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(21.5f, 2.0f)
                lineTo(21.5f, 2.0f)
                arcTo(5.0f, 5.0f, 0.0f, false, true, 26.5f, 7.0f)
                lineTo(26.5f, 7.0f)
                arcTo(5.0f, 5.0f, 0.0f, false, true, 21.5f, 12.0f)
                lineTo(21.5f, 12.0f)
                arcTo(5.0f, 5.0f, 0.0f, false, true, 16.5f, 7.0f)
                lineTo(16.5f, 7.0f)
                arcTo(5.0f, 5.0f, 0.0f, false, true, 21.5f, 2.0f)
                close()
            }
        }
        .build()
        return _bellon!!
    }

private var _bellon: ImageVector? = null
