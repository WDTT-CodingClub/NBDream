package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Community: ImageVector
    get() {
        if (_community != null) {
            return _community!!
        }
        _community = Builder(name = "Community", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.0f, 3.069f)
                curveTo(1.0f, 1.9263f, 1.9029f, 1.0f, 3.0166f, 1.0f)
                horizontalLineTo(11.8115f)
                curveTo(12.9252f, 1.0f, 13.8281f, 1.9263f, 13.8281f, 3.069f)
                verticalLineTo(15.9633f)
                curveTo(13.8281f, 17.1059f, 12.9252f, 18.0322f, 11.8115f, 18.0322f)
                horizontalLineTo(3.0166f)
                curveTo(1.9029f, 18.0322f, 1.0f, 17.1059f, 1.0f, 15.9633f)
                verticalLineTo(3.069f)
                close()
            }
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(18.1719f, 16.0366f)
                curveTo(18.1719f, 14.894f, 19.0747f, 13.9677f, 20.1885f, 13.9677f)
                horizontalLineTo(28.9834f)
                curveTo(30.0971f, 13.9677f, 30.9999f, 14.894f, 30.9999f, 16.0366f)
                verticalLineTo(28.9309f)
                curveTo(30.9999f, 30.0736f, 30.0971f, 30.9999f, 28.9834f, 30.9999f)
                horizontalLineTo(20.1885f)
                curveTo(19.0747f, 30.9999f, 18.1719f, 30.0736f, 18.1719f, 28.9309f)
                verticalLineTo(16.0366f)
                close()
            }
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(18.1719f, 3.069f)
                curveTo(18.1719f, 1.9263f, 19.0747f, 1.0f, 20.1885f, 1.0f)
                horizontalLineTo(28.9834f)
                curveTo(30.0971f, 1.0f, 30.9999f, 1.9263f, 30.9999f, 3.069f)
                verticalLineTo(7.4472f)
                curveTo(30.9999f, 8.5898f, 30.0971f, 9.5161f, 28.9834f, 9.5161f)
                horizontalLineTo(20.1885f)
                curveTo(19.0747f, 9.5161f, 18.1719f, 8.5898f, 18.1719f, 7.4472f)
                verticalLineTo(3.069f)
                close()
            }
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.0f, 24.5529f)
                curveTo(1.0f, 23.4102f, 1.9029f, 22.4839f, 3.0166f, 22.4839f)
                horizontalLineTo(11.8115f)
                curveTo(12.9252f, 22.4839f, 13.8281f, 23.4102f, 13.8281f, 24.5529f)
                verticalLineTo(28.931f)
                curveTo(13.8281f, 30.0737f, 12.9252f, 31.0f, 11.8115f, 31.0f)
                horizontalLineTo(3.0166f)
                curveTo(1.9029f, 31.0f, 1.0f, 30.0737f, 1.0f, 28.931f)
                verticalLineTo(24.5529f)
                close()
            }
        }
        .build()
        return _community!!
    }

private var _community: ImageVector? = null
