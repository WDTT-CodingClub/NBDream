package dreamicon

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

public val DreamIcon.Calendar: ImageVector
    get() {
        if (_calendar != null) {
            return _calendar!!
        }
        _calendar = Builder(name = "Calendar", defaultWidth = 30.0.dp, defaultHeight = 30.0.dp,
                viewportWidth = 30.0f, viewportHeight = 30.0f).apply {
            path(fill = SolidColor(Color(0xFF5BA455)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(3.956f, 0.0f)
                curveTo(1.7712f, 0.0f, 0.0f, 1.781f, 0.0f, 3.9779f)
                verticalLineTo(26.0221f)
                curveTo(0.0f, 28.219f, 1.7712f, 30.0f, 3.956f, 30.0f)
                horizontalLineTo(26.044f)
                curveTo(28.2288f, 30.0f, 30.0f, 28.219f, 30.0f, 26.0221f)
                verticalLineTo(3.9779f)
                curveTo(30.0f, 1.781f, 28.2288f, 0.0f, 26.044f, 0.0f)
                horizontalLineTo(3.956f)
                close()
                moveTo(22.4646f, 7.0f)
                horizontalLineTo(7.2998f)
                verticalLineTo(8.326f)
                horizontalLineTo(22.4646f)
                verticalLineTo(7.0f)
                close()
                moveTo(21.2935f, 12.6205f)
                verticalLineTo(24.072f)
                horizontalLineTo(19.5697f)
                verticalLineTo(14.3539f)
                horizontalLineTo(19.503f)
                lineTo(16.7781f, 16.1432f)
                verticalLineTo(14.4881f)
                lineTo(19.6197f, 12.6205f)
                horizontalLineTo(21.2935f)
                close()
                moveTo(8.9741f, 23.8315f)
                curveTo(9.5747f, 24.0962f, 10.2568f, 24.2285f, 11.0205f, 24.2285f)
                curveTo(11.799f, 24.2285f, 12.4904f, 24.0869f, 13.0947f, 23.8036f)
                curveTo(13.699f, 23.5165f, 14.1753f, 23.1233f, 14.5238f, 22.6237f)
                curveTo(14.876f, 22.1242f, 15.0503f, 21.5595f, 15.0465f, 20.9295f)
                curveTo(15.0503f, 20.2063f, 14.8426f, 19.6006f, 14.4237f, 19.1122f)
                curveTo(14.0085f, 18.6239f, 13.4228f, 18.322f, 12.6665f, 18.2064f)
                verticalLineTo(18.1169f)
                curveTo(13.2597f, 17.9641f, 13.7342f, 17.664f, 14.0901f, 17.2167f)
                curveTo(14.4497f, 16.7694f, 14.6276f, 16.2251f, 14.6239f, 15.584f)
                curveTo(14.6276f, 15.0211f, 14.483f, 14.5029f, 14.1902f, 14.0295f)
                curveTo(13.901f, 13.5561f, 13.4895f, 13.1777f, 12.9557f, 12.8944f)
                curveTo(12.4255f, 12.6074f, 11.7953f, 12.4639f, 11.065f, 12.4639f)
                curveTo(10.368f, 12.4639f, 9.7341f, 12.5962f, 9.1632f, 12.8609f)
                curveTo(8.5923f, 13.1255f, 8.1326f, 13.4927f, 7.7841f, 13.9624f)
                curveTo(7.4393f, 14.4321f, 7.2576f, 14.9763f, 7.2391f, 15.5951f)
                horizontalLineTo(8.9018f)
                curveTo(8.9166f, 15.2447f, 9.0241f, 14.9447f, 9.2243f, 14.6949f)
                curveTo(9.4245f, 14.4414f, 9.684f, 14.2476f, 10.0029f, 14.1134f)
                curveTo(10.3254f, 13.9755f, 10.672f, 13.9065f, 11.0427f, 13.9065f)
                curveTo(11.432f, 13.9065f, 11.7694f, 13.981f, 12.0548f, 14.1302f)
                curveTo(12.344f, 14.2793f, 12.5664f, 14.4862f, 12.7221f, 14.7508f)
                curveTo(12.8815f, 15.0118f, 12.9612f, 15.3193f, 12.9612f, 15.6734f)
                curveTo(12.9612f, 16.0425f, 12.8704f, 16.3649f, 12.6887f, 16.6408f)
                curveTo(12.5071f, 16.9129f, 12.255f, 17.1254f, 11.9325f, 17.2782f)
                curveTo(11.6136f, 17.431f, 11.2429f, 17.5075f, 10.8203f, 17.5075f)
                horizontalLineTo(9.8583f)
                verticalLineTo(18.9165f)
                horizontalLineTo(10.8203f)
                curveTo(11.3467f, 18.9165f, 11.7953f, 19.0004f, 12.166f, 19.1682f)
                curveTo(12.5405f, 19.3359f, 12.8241f, 19.567f, 13.0168f, 19.8615f)
                curveTo(13.2133f, 20.1523f, 13.3116f, 20.4896f, 13.3116f, 20.8736f)
                curveTo(13.3116f, 21.2426f, 13.2133f, 21.5688f, 13.0168f, 21.8521f)
                curveTo(12.8204f, 22.1317f, 12.5479f, 22.3498f, 12.1994f, 22.5063f)
                curveTo(11.8546f, 22.6629f, 11.4579f, 22.7412f, 11.0094f, 22.7412f)
                curveTo(10.6016f, 22.7412f, 10.2309f, 22.6741f, 9.8972f, 22.5399f)
                curveTo(9.5635f, 22.4057f, 9.2929f, 22.2156f, 9.0853f, 21.9695f)
                curveTo(8.8814f, 21.7198f, 8.7683f, 21.4253f, 8.7461f, 21.0861f)
                horizontalLineTo(7.0f)
                curveTo(7.0259f, 21.7086f, 7.2113f, 22.2566f, 7.5561f, 22.73f)
                curveTo(7.9046f, 23.1997f, 8.3772f, 23.5669f, 8.9741f, 23.8315f)
                close()
            }
        }
        .build()
        return _calendar!!
    }

private var _calendar: ImageVector? = null
