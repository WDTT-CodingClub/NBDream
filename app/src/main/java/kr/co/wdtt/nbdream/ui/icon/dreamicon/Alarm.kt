package kr.co.wdtt.nbdream.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon

public val DreamIcon.Alarm: ImageVector
    get() {
        if (_alarm != null) {
            return _alarm!!
        }
        _alarm = Builder(name = "Alarm", defaultWidth = 13.0.dp, defaultHeight = 13.0.dp,
                viewportWidth = 13.0f, viewportHeight = 13.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFFFFC700)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(17.056f, -1.583f)
                    horizontalLineTo(-2.329f)
                    verticalLineToRelative(19.167f)
                    horizontalLineToRelative(19.385f)
                    close()
                }
            }
        }
        .build()
        return _alarm!!
    }

private var _alarm: ImageVector? = null
