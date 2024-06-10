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

public val DreamIcon.Chat: ImageVector
    get() {
        if (_chat != null) {
            return _chat!!
        }
        _chat = Builder(name = "Chat", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(16.3014f, 1.0f)
                curveTo(8.1835f, 1.0f, 1.6027f, 7.7157f, 1.6027f, 16.0f)
                curveTo(1.6027f, 19.7974f, 2.9867f, 23.2664f, 5.2655f, 25.9081f)
                lineTo(1.7158f, 28.154f)
                curveTo(0.4261f, 28.97f, 0.9928f, 31.0f, 2.5102f, 31.0f)
                horizontalLineTo(16.132f)
                curveTo(16.14f, 31.0f, 16.148f, 30.9999f, 16.156f, 30.9998f)
                curveTo(16.1617f, 30.9997f, 16.1675f, 30.9996f, 16.1733f, 30.9994f)
                curveTo(16.2159f, 30.9998f, 16.2586f, 31.0f, 16.3014f, 31.0f)
                curveTo(24.4192f, 31.0f, 31.0f, 24.2843f, 31.0f, 16.0f)
                curveTo(31.0f, 7.7157f, 24.4192f, 1.0f, 16.3014f, 1.0f)
                close()
                moveTo(7.7749f, 12.2307f)
                curveTo(7.4419f, 12.2307f, 7.1719f, 12.5062f, 7.1719f, 12.8461f)
                curveTo(7.1719f, 13.186f, 7.4419f, 13.4615f, 7.7749f, 13.4615f)
                horizontalLineTo(23.9057f)
                curveTo(24.2387f, 13.4615f, 24.5087f, 13.186f, 24.5087f, 12.8461f)
                curveTo(24.5087f, 12.5062f, 24.2388f, 12.2307f, 23.9057f, 12.2307f)
                horizontalLineTo(7.7749f)
                close()
                moveTo(7.1719f, 17.923f)
                curveTo(7.1719f, 17.5831f, 7.4419f, 17.3076f, 7.7749f, 17.3076f)
                horizontalLineTo(16.0664f)
                curveTo(16.3995f, 17.3076f, 16.6695f, 17.5831f, 16.6695f, 17.923f)
                curveTo(16.6695f, 18.2629f, 16.3995f, 18.5384f, 16.0664f, 18.5384f)
                horizontalLineTo(7.7749f)
                curveTo(7.4419f, 18.5384f, 7.1719f, 18.2629f, 7.1719f, 17.923f)
                close()
            }
        }
        .build()
        return _chat!!
    }

private var _chat: ImageVector? = null
