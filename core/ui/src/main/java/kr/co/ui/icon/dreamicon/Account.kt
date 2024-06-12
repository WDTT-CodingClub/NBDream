package dreamicon

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
                moveTo(18.9228f, 1.2847f)
                horizontalLineTo(7.0828f)
                curveTo(4.8099f, 1.2847f, 2.9673f, 3.0318f, 2.9673f, 5.1871f)
                verticalLineTo(26.813f)
                curveTo(2.9673f, 28.9683f, 4.8099f, 30.7154f, 7.0828f, 30.7154f)
                horizontalLineTo(24.9169f)
                curveTo(27.1899f, 30.7154f, 29.0325f, 28.9683f, 29.0325f, 26.813f)
                verticalLineTo(11.219f)
                curveTo(29.0052f, 11.2221f, 28.9774f, 11.2237f, 28.9491f, 11.2237f)
                horizontalLineTo(19.6034f)
                curveTo(19.2246f, 11.2237f, 18.9175f, 10.9325f, 18.9175f, 10.5733f)
                verticalLineTo(1.3659f)
                curveTo(18.9175f, 1.3384f, 18.9193f, 1.3113f, 18.9228f, 1.2847f)
                close()
                moveTo(28.72f, 9.9229f)
                lineTo(20.2893f, 1.9289f)
                verticalLineTo(9.9229f)
                horizontalLineTo(28.72f)
                close()
                moveTo(6.7399f, 19.1708f)
                curveTo(6.3611f, 19.1708f, 6.0539f, 19.462f, 6.0539f, 19.8212f)
                curveTo(6.0539f, 20.1804f, 6.3611f, 20.4716f, 6.7399f, 20.4716f)
                horizontalLineTo(25.0884f)
                curveTo(25.4672f, 20.4716f, 25.7743f, 20.1804f, 25.7743f, 19.8212f)
                curveTo(25.7743f, 19.462f, 25.4672f, 19.1708f, 25.0884f, 19.1708f)
                horizontalLineTo(6.7399f)
                close()
                moveTo(6.0539f, 23.8862f)
                curveTo(6.0539f, 23.527f, 6.3611f, 23.2358f, 6.7399f, 23.2358f)
                horizontalLineTo(25.0884f)
                curveTo(25.4672f, 23.2358f, 25.7743f, 23.527f, 25.7743f, 23.8862f)
                curveTo(25.7743f, 24.2454f, 25.4672f, 24.5366f, 25.0884f, 24.5366f)
                horizontalLineTo(6.7399f)
                curveTo(6.3611f, 24.5366f, 6.0539f, 24.2454f, 6.0539f, 23.8862f)
                close()
            }
        }
        .build()
        return _account!!
    }

private var _account: ImageVector? = null
