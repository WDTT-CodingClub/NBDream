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

public val DreamIcon.Account: ImageVector
    get() {
        if (_account != null) {
            return _account!!
        }
        _account = Builder(name = "Account", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(28.7312f, 10.5038f)
                lineTo(19.3087f, 1.5692f)
                horizontalLineTo(6.7816f)
                curveTo(4.5086f, 1.5692f, 2.666f, 3.3164f, 2.666f, 5.4716f)
                verticalLineTo(27.0976f)
                curveTo(2.666f, 29.2528f, 4.5086f, 31.0f, 6.7816f, 31.0f)
                horizontalLineTo(24.6156f)
                curveTo(26.8886f, 31.0f, 28.7312f, 29.2528f, 28.7312f, 27.0976f)
                verticalLineTo(10.5038f)
                close()
                moveTo(5.7527f, 20.1057f)
                curveTo(5.7527f, 19.7465f, 6.0598f, 19.4553f, 6.4386f, 19.4553f)
                horizontalLineTo(24.7871f)
                curveTo(25.166f, 19.4553f, 25.4731f, 19.7465f, 25.4731f, 20.1057f)
                curveTo(25.4731f, 20.4649f, 25.166f, 20.7561f, 24.7871f, 20.7561f)
                horizontalLineTo(6.4386f)
                curveTo(6.0598f, 20.7561f, 5.7527f, 20.4649f, 5.7527f, 20.1057f)
                close()
                moveTo(6.4386f, 23.5203f)
                curveTo(6.0598f, 23.5203f, 5.7527f, 23.8115f, 5.7527f, 24.1707f)
                curveTo(5.7527f, 24.53f, 6.0598f, 24.8211f, 6.4386f, 24.8211f)
                horizontalLineTo(24.7871f)
                curveTo(25.166f, 24.8211f, 25.4731f, 24.53f, 25.4731f, 24.1707f)
                curveTo(25.4731f, 23.8115f, 25.166f, 23.5203f, 24.7871f, 23.5203f)
                horizontalLineTo(6.4386f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(19.3021f, 1.0f)
                curveTo(19.681f, 1.0f, 19.9881f, 1.2912f, 19.9881f, 1.6504f)
                verticalLineTo(10.2074f)
                horizontalLineTo(28.6479f)
                curveTo(29.0267f, 10.2074f, 29.3338f, 10.4986f, 29.3338f, 10.8579f)
                curveTo(29.3338f, 11.2171f, 29.0267f, 11.5083f, 28.6479f, 11.5083f)
                horizontalLineTo(19.3021f)
                curveTo(18.9233f, 11.5083f, 18.6162f, 11.2171f, 18.6162f, 10.8579f)
                verticalLineTo(1.6504f)
                curveTo(18.6162f, 1.2912f, 18.9233f, 1.0f, 19.3021f, 1.0f)
                close()
            }
        }
        .build()
        return _account!!
    }

private var _account: ImageVector? = null
