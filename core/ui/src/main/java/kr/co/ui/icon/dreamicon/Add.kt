package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Add: ImageVector
    get() {
        if (_add != null) {
            return _add!!
        }
        _add = Builder(name = "Add", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp, viewportWidth
                = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9E9E9E)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 4.1665f)
                verticalLineTo(15.8332f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9E9E9E)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(4.1667f, 10.0f)
                horizontalLineTo(15.8333f)
            }
        }
        .build()
        return _add!!
    }

private var _add: ImageVector? = null
