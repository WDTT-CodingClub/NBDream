package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
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

public val DreamIcon.Sunny: ImageVector
    get() {
        if (_sunny != null) {
            return _sunny!!
        }
        _sunny = Builder(name = "Sunny", defaultWidth = 112.0.dp, defaultHeight = 78.0.dp,
                viewportWidth = 112.0f, viewportHeight = 78.0f).apply {
            path(fill = SolidColor(Color(0xFFFFB900)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(84.333f, 23.667f)
                moveToRelative(-21.667f, 0.0f)
                arcToRelative(21.667f, 21.667f, 0.0f, true, true, 43.333f, 0.0f)
                arcToRelative(21.667f, 21.667f, 0.0f, true, true, -43.333f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFF6FBFE)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(86.5f, 71.333f)
                horizontalLineTo(23.667f)
                lineTo(56.167f, 45.333f)
                lineTo(86.5f, 71.333f)
                close()
            }
            path(fill = SolidColor(Color(0xFFF6FBFE)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(84.333f, 49.667f)
                moveToRelative(-21.667f, 0.0f)
                arcToRelative(21.667f, 21.667f, 0.0f, true, true, 43.333f, 0.0f)
                arcToRelative(21.667f, 21.667f, 0.0f, true, true, -43.333f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFF6FBFE)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(51.833f, 34.5f)
                moveToRelative(-32.5f, 0.0f)
                arcToRelative(32.5f, 32.5f, 0.0f, true, true, 65.0f, 0.0f)
                arcToRelative(32.5f, 32.5f, 0.0f, true, true, -65.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFF6FBFE)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(23.667f, 49.667f)
                moveToRelative(-21.667f, 0.0f)
                arcToRelative(21.667f, 21.667f, 0.0f, true, true, 43.333f, 0.0f)
                arcToRelative(21.667f, 21.667f, 0.0f, true, true, -43.333f, 0.0f)
            }
        }
        .build()
        return _sunny!!
    }

private var _sunny: ImageVector? = null
