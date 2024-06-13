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

public val DreamIcon.Defaultprofile: ImageVector
    get() {
        if (_defaultprofile != null) {
            return _defaultprofile!!
        }
        _defaultprofile = Builder(name = "Defaultprofile", defaultWidth = 88.0.dp, defaultHeight =
                88.0.dp, viewportWidth = 88.0f, viewportHeight = 88.0f).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(44.0f, 44.0f)
                moveToRelative(-44.0f, 0.0f)
                arcToRelative(44.0f, 44.0f, 0.0f, true, true, 88.0f, 0.0f)
                arcToRelative(44.0f, 44.0f, 0.0f, true, true, -88.0f, 0.0f)
            }
            path(fill = null, stroke = null, strokeLineWidth = 0.0f, strokeLineCap = Butt,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(14.0f, 13.0f)
                horizontalLineToRelative(59.3281f)
                verticalLineToRelative(62.2459f)
                horizontalLineToRelative(-59.3281f)
                close()
            }
            path(fill = null, stroke = null, strokeLineWidth = 0.0f, strokeLineCap = Butt,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(23.4014f, 39.26f)
                horizontalLineToRelative(25.6116f)
                verticalLineToRelative(20.1002f)
                horizontalLineToRelative(-25.6116f)
                close()
            }
        }
        .build()
        return _defaultprofile!!
    }

private var _defaultprofile: ImageVector? = null
