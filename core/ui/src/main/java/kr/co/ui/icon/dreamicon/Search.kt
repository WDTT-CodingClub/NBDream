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

public val DreamIcon.Search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search = Builder(name = "Search", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFFBDBCBD)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(4.0f, 11.0f)
                curveTo(4.0f, 7.134f, 7.134f, 4.0f, 11.0f, 4.0f)
                curveTo(14.866f, 4.0f, 18.0f, 7.134f, 18.0f, 11.0f)
                curveTo(18.0f, 12.8857f, 17.2543f, 14.5973f, 16.0418f, 15.856f)
                curveTo(16.0074f, 15.8824f, 15.9744f, 15.9113f, 15.9429f, 15.9428f)
                curveTo(15.9114f, 15.9743f, 15.8825f, 16.0073f, 15.8561f, 16.0417f)
                curveTo(14.5974f, 17.2543f, 12.8858f, 18.0f, 11.0f, 18.0f)
                curveTo(7.134f, 18.0f, 4.0f, 14.866f, 4.0f, 11.0f)
                close()
                moveTo(16.6177f, 18.0319f)
                curveTo(15.0781f, 19.2635f, 13.125f, 20.0f, 11.0f, 20.0f)
                curveTo(6.0294f, 20.0f, 2.0f, 15.9706f, 2.0f, 11.0f)
                curveTo(2.0f, 6.0294f, 6.0294f, 2.0f, 11.0f, 2.0f)
                curveTo(15.9706f, 2.0f, 20.0f, 6.0294f, 20.0f, 11.0f)
                curveTo(20.0f, 13.125f, 19.2635f, 15.078f, 18.0319f, 16.6176f)
                lineTo(21.7071f, 20.2928f)
                curveTo(22.0976f, 20.6833f, 22.0976f, 21.3165f, 21.7071f, 21.707f)
                curveTo(21.3166f, 22.0975f, 20.6834f, 22.0975f, 20.2929f, 21.707f)
                lineTo(16.6177f, 18.0319f)
                close()
            }
        }
        .build()
        return _search!!
    }

private var _search: ImageVector? = null
