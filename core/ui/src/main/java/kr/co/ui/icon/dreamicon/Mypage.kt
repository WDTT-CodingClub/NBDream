package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Mypage: ImageVector
    get() {
        if (_mypage != null) {
            return _mypage!!
        }
        _mypage = Builder(name = "Mypage", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(24.3316f, 9.0186f)
                curveTo(24.3316f, 13.4622f, 20.6014f, 17.0645f, 16.0f, 17.0645f)
                curveTo(11.3986f, 17.0645f, 7.6685f, 13.4622f, 7.6685f, 9.0186f)
                curveTo(7.6685f, 4.5749f, 11.3986f, 0.9727f, 16.0f, 0.9727f)
                curveTo(20.6014f, 0.9727f, 24.3316f, 4.5749f, 24.3316f, 9.0186f)
                close()
            }
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(2.5f, 31.0273f)
                horizontalLineTo(29.5f)
                curveTo(29.4585f, 23.8613f, 23.4302f, 18.0645f, 16.0f, 18.0645f)
                curveTo(8.5698f, 18.0645f, 2.5415f, 23.8613f, 2.5f, 31.0273f)
                close()
            }
        }
        .build()
        return _mypage!!
    }

private var _mypage: ImageVector? = null
