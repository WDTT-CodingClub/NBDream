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

public val DreamIcon.Addpicture: ImageVector
    get() {
        if (_addpicture != null) {
            return _addpicture!!
        }
        _addpicture = Builder(name = "Addpicture", defaultWidth = 41.0.dp, defaultHeight = 41.0.dp,
                viewportWidth = 41.0f, viewportHeight = 41.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(22.5f, 6.0f)
                lineTo(22.5f, 6.0f)
                arcTo(16.5f, 16.5f, 0.0f, false, true, 39.0f, 22.5f)
                lineTo(39.0f, 22.5f)
                arcTo(16.5f, 16.5f, 0.0f, false, true, 22.5f, 39.0f)
                lineTo(22.5f, 39.0f)
                arcTo(16.5f, 16.5f, 0.0f, false, true, 6.0f, 22.5f)
                lineTo(6.0f, 22.5f)
                arcTo(16.5f, 16.5f, 0.0f, false, true, 22.5f, 6.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF606160)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(13.0f, 17.0f)
                curveTo(13.0f, 14.7909f, 14.7909f, 13.0f, 17.0f, 13.0f)
                horizontalLineTo(28.0f)
                curveTo(30.2091f, 13.0f, 32.0f, 14.7909f, 32.0f, 17.0f)
                verticalLineTo(28.0f)
                curveTo(32.0f, 30.2091f, 30.2091f, 32.0f, 28.0f, 32.0f)
                horizontalLineTo(17.0f)
                curveTo(14.7909f, 32.0f, 13.0f, 30.2091f, 13.0f, 28.0f)
                verticalLineTo(17.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF606160)),
                    strokeLineWidth = 1.5f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.604f, 25.104f)
                lineTo(16.1178f, 21.5902f)
                curveTo(17.0002f, 20.7079f, 18.4661f, 20.8401f, 19.1764f, 21.866f)
                lineTo(21.2663f, 24.8846f)
                curveTo(21.931f, 25.8447f, 23.2733f, 26.0335f, 24.1771f, 25.2941f)
                lineTo(26.8282f, 23.1251f)
                curveTo(27.6234f, 22.4744f, 28.7823f, 22.5322f, 29.5088f, 23.2588f)
                lineTo(32.3957f, 26.1456f)
            }
            path(fill = SolidColor(Color(0xFF606160)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(27.1875f, 17.8125f)
                moveToRelative(-1.5625f, 0.0f)
                arcToRelative(1.5625f, 1.5625f, 0.0f, true, true, 3.125f, 0.0f)
                arcToRelative(1.5625f, 1.5625f, 0.0f, true, true, -3.125f, 0.0f)
            }
        }
        .build()
        return _addpicture!!
    }

private var _addpicture: ImageVector? = null
