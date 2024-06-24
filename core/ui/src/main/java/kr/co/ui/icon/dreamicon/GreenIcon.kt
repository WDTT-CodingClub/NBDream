package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.GreenIcon: ImageVector
    get() {
        if (_greenicon != null) {
            return _greenicon!!
        }
        _greenicon = Builder(name = "Green icon", defaultWidth = 14.0.dp, defaultHeight =
                14.0.dp, viewportWidth = 14.0f, viewportHeight = 14.0f).apply {
            path(fill = SolidColor(Color(0xFF71C16B)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(8.0f, 13.9291f)
                curveTo(11.3923f, 13.4439f, 14.0f, 10.5265f, 14.0f, 7.0f)
                curveTo(14.0f, 3.134f, 10.866f, 0.0f, 7.0f, 0.0f)
                curveTo(3.134f, 0.0f, 0.0f, 3.134f, 0.0f, 7.0f)
                curveTo(0.0f, 10.866f, 3.134f, 14.0f, 7.0f, 14.0f)
                verticalLineTo(9.1582f)
                curveTo(4.8998f, 8.8628f, 2.0242f, 7.8072f, 2.7202f, 5.2096f)
                curveTo(3.9725f, 5.1896f, 6.2885f, 5.683f, 7.3402f, 7.5788f)
                curveTo(7.6663f, 5.9458f, 8.6253f, 4.1243f, 10.8585f, 4.7227f)
                curveTo(10.9292f, 5.9001f, 10.4227f, 8.1848f, 8.0f, 8.7359f)
                verticalLineTo(13.9291f)
                close()
            }
        }
        .build()
        return _greenicon!!
    }

private var _greenicon: ImageVector? = null