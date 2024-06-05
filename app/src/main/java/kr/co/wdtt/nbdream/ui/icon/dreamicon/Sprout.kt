package kr.co.wdtt.nbdream.ui.icon.dreamicon

import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon

public val DreamIcon.Sprout: ImageVector
    get() {
        if (_sprout != null) {
            return _sprout!!
        }
        _sprout = Builder(name = "Sprout", defaultWidth = 31.0.dp, defaultHeight = 30.0.dp,
                viewportWidth = 31.0f, viewportHeight = 30.0f).apply {
            path(fill = null, stroke = null, strokeLineWidth = 0.0f, strokeLineCap = Butt,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(31.0f)
                verticalLineToRelative(30.0f)
                horizontalLineTo(0.0f)
                close()
            }
        }
        .build()
        return _sprout!!
    }

private var _sprout: ImageVector? = null
