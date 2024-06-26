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

public val DreamIcon.Delete: ImageVector
    get() {
        if (_delete != null) {
            return _delete!!
        }
        _delete = Builder(name = "Delete", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF747575)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.8619f, 2.8618f)
                curveTo(7.9869f, 2.7367f, 8.1565f, 2.6665f, 8.3333f, 2.6665f)
                horizontalLineTo(11.6667f)
                curveTo(11.8435f, 2.6665f, 12.013f, 2.7367f, 12.1381f, 2.8618f)
                curveTo(12.2631f, 2.9868f, 12.3333f, 3.1564f, 12.3333f, 3.3332f)
                verticalLineTo(4.0f)
                horizontalLineTo(7.6667f)
                verticalLineTo(3.3332f)
                curveTo(7.6667f, 3.1564f, 7.7369f, 2.9868f, 7.8619f, 2.8618f)
                close()
                moveTo(5.6667f, 4.0f)
                verticalLineTo(3.3332f)
                curveTo(5.6667f, 2.6259f, 5.9476f, 1.9476f, 6.4477f, 1.4476f)
                curveTo(6.9478f, 0.9475f, 7.6261f, 0.6665f, 8.3333f, 0.6665f)
                horizontalLineTo(11.6667f)
                curveTo(12.3739f, 0.6665f, 13.0522f, 0.9475f, 13.5523f, 1.4476f)
                curveTo(14.0524f, 1.9476f, 14.3333f, 2.6259f, 14.3333f, 3.3332f)
                verticalLineTo(4.0f)
                horizontalLineTo(15.8151f)
                curveTo(15.8212f, 3.9999f, 15.8272f, 3.9998f, 15.8333f, 3.9998f)
                curveTo(15.8394f, 3.9998f, 15.8455f, 3.9999f, 15.8515f, 4.0f)
                horizontalLineTo(17.5f)
                curveTo(18.0523f, 4.0f, 18.5f, 4.4477f, 18.5f, 5.0f)
                curveTo(18.5f, 5.5523f, 18.0523f, 6.0f, 17.5f, 6.0f)
                horizontalLineTo(16.8333f)
                verticalLineTo(16.6665f)
                curveTo(16.8333f, 17.3737f, 16.5524f, 18.052f, 16.0523f, 18.5521f)
                curveTo(15.5522f, 19.0522f, 14.8739f, 19.3332f, 14.1667f, 19.3332f)
                horizontalLineTo(5.8333f)
                curveTo(5.1261f, 19.3332f, 4.4478f, 19.0522f, 3.9477f, 18.5521f)
                curveTo(3.4476f, 18.052f, 3.1667f, 17.3737f, 3.1667f, 16.6665f)
                verticalLineTo(6.0f)
                horizontalLineTo(2.5f)
                curveTo(1.9477f, 6.0f, 1.5f, 5.5523f, 1.5f, 5.0f)
                curveTo(1.5f, 4.4477f, 1.9477f, 4.0f, 2.5f, 4.0f)
                horizontalLineTo(4.1484f)
                curveTo(4.1545f, 3.9999f, 4.1606f, 3.9998f, 4.1667f, 3.9998f)
                curveTo(4.1727f, 3.9998f, 4.1788f, 3.9999f, 4.1849f, 4.0f)
                horizontalLineTo(5.6667f)
                close()
                moveTo(5.1667f, 6.0f)
                verticalLineTo(16.6665f)
                curveTo(5.1667f, 16.8433f, 5.2369f, 17.0129f, 5.3619f, 17.1379f)
                curveTo(5.4869f, 17.2629f, 5.6565f, 17.3332f, 5.8333f, 17.3332f)
                horizontalLineTo(14.1667f)
                curveTo(14.3435f, 17.3332f, 14.513f, 17.2629f, 14.6381f, 17.1379f)
                curveTo(14.7631f, 17.0129f, 14.8333f, 16.8433f, 14.8333f, 16.6665f)
                verticalLineTo(6.0f)
                horizontalLineTo(5.1667f)
                close()
            }
        }
        .build()
        return _delete!!
    }

private var _delete: ImageVector? = null
