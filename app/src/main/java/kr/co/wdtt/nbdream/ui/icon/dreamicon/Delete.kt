package kr.co.wdtt.nbdream.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon

public val DreamIcon.Delete: ImageVector
    get() {
        if (_delete != null) {
            return _delete!!
        }
        _delete = Builder(name = "Delete", defaultWidth = 13.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 13.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF292929)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveToRelative(4.0f, 1.0f)
                lineToRelative(1.0f, -1.0f)
                horizontalLineToRelative(3.0f)
                lineToRelative(1.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(2.0f)
                lineTo(0.0f, 3.0f)
                lineTo(0.0f, 1.0f)
                horizontalLineToRelative(4.0f)
                close()
                moveTo(1.0f, 4.0f)
                horizontalLineToRelative(11.0f)
                verticalLineToRelative(11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, 1.0f)
                lineTo(2.0f, 16.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                lineTo(1.0f, 4.0f)
                close()
                moveTo(3.35f, 6.0f)
                horizontalLineToRelative(1.5f)
                verticalLineToRelative(8.5f)
                horizontalLineToRelative(-1.5f)
                lineTo(3.35f, 6.0f)
                close()
                moveTo(9.85f, 6.0f)
                horizontalLineToRelative(-1.5f)
                verticalLineToRelative(8.5f)
                horizontalLineToRelative(1.5f)
                lineTo(9.85f, 6.0f)
                close()
            }
        }
        .build()
        return _delete!!
    }

private var _delete: ImageVector? = null
