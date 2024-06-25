package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.DatePicker: ImageVector
    get() {
        if (_datepicker != null) {
            return _datepicker!!
        }
        _datepicker = Builder(name = "DatePicker", defaultWidth = 18.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 18.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF747575)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.3333f, 1.6665f)
                verticalLineTo(4.9998f)
                moveTo(5.6667f, 1.6665f)
                verticalLineTo(4.9998f)
                moveTo(1.5f, 8.3332f)
                horizontalLineTo(16.5f)
                moveTo(3.1667f, 3.3332f)
                horizontalLineTo(14.8333f)
                curveTo(15.7538f, 3.3332f, 16.5f, 4.0794f, 16.5f, 4.9998f)
                verticalLineTo(16.6665f)
                curveTo(16.5f, 17.587f, 15.7538f, 18.3332f, 14.8333f, 18.3332f)
                horizontalLineTo(3.1667f)
                curveTo(2.2462f, 18.3332f, 1.5f, 17.587f, 1.5f, 16.6665f)
                verticalLineTo(4.9998f)
                curveTo(1.5f, 4.0794f, 2.2462f, 3.3332f, 3.1667f, 3.3332f)
                close()
            }
        }
        .build()
        return _datepicker!!
    }

private var _datepicker: ImageVector? = null
