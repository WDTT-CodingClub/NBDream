package kr.co.ui.icon.dreamicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.OutlineEdit: ImageVector
    get() {
        if (_outlineedit != null) {
            return _outlineedit!!
        }
        _outlineedit = Builder(name = "Outlineedit", defaultWidth = 33.0.dp, defaultHeight =
                32.0.dp, viewportWidth = 33.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(6.6262f, 25.8736f)
                lineTo(7.3333f, 25.1665f)
                lineTo(6.6262f, 25.8736f)
                curveTo(6.995f, 26.2424f, 7.4469f, 26.2357f, 7.6536f, 26.2155f)
                curveTo(7.8333f, 26.1979f, 8.0362f, 26.1469f, 8.1827f, 26.1101f)
                curveTo(8.1947f, 26.1071f, 8.2063f, 26.1042f, 8.2175f, 26.1014f)
                lineTo(12.2049f, 25.1045f)
                curveTo(12.2229f, 25.1f, 12.2413f, 25.0955f, 12.2602f, 25.0908f)
                curveTo(12.4685f, 25.0394f, 12.7322f, 24.9743f, 12.974f, 24.8374f)
                curveTo(13.2158f, 24.7005f, 13.4072f, 24.5079f, 13.5585f, 24.3558f)
                curveTo(13.5722f, 24.3419f, 13.5856f, 24.3285f, 13.5987f, 24.3154f)
                lineTo(23.7927f, 14.1213f)
                curveTo(23.8056f, 14.1085f, 23.8184f, 14.0957f, 23.8312f, 14.0829f)
                curveTo(24.1311f, 13.7831f, 24.4226f, 13.4917f, 24.6306f, 13.219f)
                curveTo(24.8652f, 12.9116f, 25.0856f, 12.5137f, 25.0856f, 12.0f)
                curveTo(25.0856f, 11.4863f, 24.8652f, 11.0884f, 24.6306f, 10.781f)
                curveTo(24.4226f, 10.5083f, 24.1311f, 10.217f, 23.8312f, 9.9172f)
                curveTo(23.8187f, 9.9046f, 23.8062f, 9.8921f, 23.7936f, 9.8795f)
                curveTo(23.7933f, 9.8793f, 23.793f, 9.879f, 23.7927f, 9.8787f)
                lineTo(22.6212f, 8.7071f)
                curveTo(22.6083f, 8.6943f, 22.5955f, 8.6814f, 22.5827f, 8.6686f)
                curveTo(22.2829f, 8.3687f, 21.9915f, 8.0772f, 21.7189f, 7.8692f)
                curveTo(21.4115f, 7.6347f, 21.0135f, 7.4142f, 20.4998f, 7.4142f)
                curveTo(19.9862f, 7.4142f, 19.5882f, 7.6347f, 19.2808f, 7.8692f)
                curveTo(19.0081f, 8.0772f, 18.7168f, 8.3687f, 18.417f, 8.6686f)
                curveTo(18.4042f, 8.6814f, 18.3914f, 8.6943f, 18.3785f, 8.7071f)
                lineTo(8.1844f, 18.9012f)
                curveTo(8.1714f, 18.9143f, 8.1579f, 18.9277f, 8.1441f, 18.9414f)
                curveTo(7.9919f, 19.0926f, 7.7993f, 19.2841f, 7.6624f, 19.5259f)
                lineTo(8.5326f, 20.0186f)
                lineTo(7.6624f, 19.5259f)
                curveTo(7.5255f, 19.7676f, 7.4605f, 20.0313f, 7.409f, 20.2396f)
                curveTo(7.4044f, 20.2585f, 7.3998f, 20.277f, 7.3953f, 20.2949f)
                lineTo(6.3985f, 24.2823f)
                curveTo(6.3957f, 24.2936f, 6.3927f, 24.3052f, 6.3897f, 24.3172f)
                curveTo(6.3529f, 24.4637f, 6.3019f, 24.6666f, 6.2843f, 24.8462f)
                curveTo(6.2641f, 25.0529f, 6.2574f, 25.5048f, 6.6262f, 25.8736f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(17.1665f, 10.0f)
                lineTo(22.4998f, 15.3333f)
            }
        }
        .build()
        return _outlineedit!!
    }

private var _outlineedit: ImageVector? = null
