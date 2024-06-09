package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Home: ImageVector
    get() {
        if (_home != null) {
            return _home!!
        }
        _home = Builder(name = "Home", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(4.956f, 13.0f)
                curveTo(2.7712f, 13.0f, 1.0f, 14.4653f, 1.0f, 16.2727f)
                verticalLineTo(27.7273f)
                curveTo(1.0f, 29.5347f, 2.7712f, 31.0f, 4.956f, 31.0f)
                horizontalLineTo(10.0659f)
                verticalLineTo(22.2727f)
                curveTo(10.0659f, 21.0677f, 11.2467f, 20.0909f, 12.7033f, 20.0909f)
                horizontalLineTo(19.7912f)
                curveTo(21.2478f, 20.0909f, 22.4286f, 21.0677f, 22.4286f, 22.2727f)
                verticalLineTo(31.0f)
                horizontalLineTo(27.044f)
                curveTo(29.2288f, 31.0f, 31.0f, 29.5347f, 31.0f, 27.7273f)
                verticalLineTo(16.2727f)
                curveTo(31.0f, 14.4653f, 29.2288f, 13.0f, 27.044f, 13.0f)
                horizontalLineTo(4.956f)
                close()
            }
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.0763f, 2.1584f)
                curveTo(14.6522f, 0.6139f, 17.3478f, 0.6139f, 18.9237f, 2.1584f)
                lineTo(29.9356f, 12.9512f)
                curveTo(32.2905f, 15.2592f, 30.4824f, 19.0f, 27.0119f, 19.0f)
                horizontalLineTo(4.9881f)
                curveTo(1.5176f, 19.0f, -0.2905f, 15.2592f, 2.0644f, 12.9512f)
                lineTo(13.0763f, 2.1584f)
                close()
            }
        }
        .build()
        return _home!!
    }

private var _home: ImageVector? = null
