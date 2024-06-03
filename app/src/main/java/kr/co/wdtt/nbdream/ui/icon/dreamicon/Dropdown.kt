package kr.co.wdtt.nbdream.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon

public val DreamIcon.Dropdown: ImageVector
    get() {
        if (_dropdown != null) {
            return _dropdown!!
        }
        _dropdown = Builder(name = "Dropdown", defaultWidth = 5.0.dp, defaultHeight = 19.0.dp,
                viewportWidth = 5.0f, viewportHeight = 19.0f).apply {
            path(fill = SolidColor(Color(0xFF292929)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.0f, 16.625f)
                curveTo(0.0f, 17.931f, 1.125f, 19.0f, 2.5f, 19.0f)
                reflectiveCurveTo(5.0f, 17.931f, 5.0f, 16.625f)
                reflectiveCurveTo(3.875f, 14.25f, 2.5f, 14.25f)
                reflectiveCurveTo(0.0f, 15.319f, 0.0f, 16.625f)
                close()
                moveTo(0.0f, 2.375f)
                curveTo(0.0f, 3.681f, 1.125f, 4.75f, 2.5f, 4.75f)
                reflectiveCurveTo(5.0f, 3.681f, 5.0f, 2.375f)
                reflectiveCurveTo(3.875f, 0.0f, 2.5f, 0.0f)
                reflectiveCurveTo(0.0f, 1.069f, 0.0f, 2.375f)
                close()
                moveTo(0.0f, 9.5f)
                curveToRelative(0.0f, 1.306f, 1.125f, 2.375f, 2.5f, 2.375f)
                reflectiveCurveTo(5.0f, 10.806f, 5.0f, 9.5f)
                reflectiveCurveTo(3.875f, 7.125f, 2.5f, 7.125f)
                reflectiveCurveTo(0.0f, 8.194f, 0.0f, 9.5f)
                close()
            }
        }
        .build()
        return _dropdown!!
    }

private var _dropdown: ImageVector? = null
