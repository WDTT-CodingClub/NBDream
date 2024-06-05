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

public val DreamIcon.Edit: ImageVector
    get() {
        if (_edit != null) {
            return _edit!!
        }
        _edit = Builder(name = "Edit", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 18.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0xFF292929)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(15.278f, 0.707f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.414f, 0.0f)
                lineToRelative(-1.578f, 1.579f)
                lineToRelative(3.428f, 3.428f)
                lineToRelative(1.579f, -1.578f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, -1.415f)
                lineTo(15.279f, 0.707f)
                close()
                moveTo(11.714f, 2.857f)
                lineTo(15.143f, 6.286f)
                lineTo(5.429f, 16.0f)
                lineToRelative(-3.715f, 0.286f)
                lineTo(2.0f, 12.57f)
                lineToRelative(9.714f, -9.714f)
                close()
            }
        }
        .build()
        return _edit!!
    }

private var _edit: ImageVector? = null
