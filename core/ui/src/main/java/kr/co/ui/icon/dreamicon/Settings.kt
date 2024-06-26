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
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

public val DreamIcon.Settings: ImageVector
    get() {
        if (_settings != null) {
            return _settings!!
        }
        _settings = Builder(name = "Settings", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            group {
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                        strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                        StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                        NonZero) {
                    moveTo(12.0f, 15.0f)
                    curveTo(13.6569f, 15.0f, 15.0f, 13.6569f, 15.0f, 12.0f)
                    curveTo(15.0f, 10.3431f, 13.6569f, 9.0f, 12.0f, 9.0f)
                    curveTo(10.3431f, 9.0f, 9.0f, 10.3431f, 9.0f, 12.0f)
                    curveTo(9.0f, 13.6569f, 10.3431f, 15.0f, 12.0f, 15.0f)
                    close()
                }
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF212121)),
                        strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                        StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType =
                        NonZero) {
                    moveTo(19.4f, 15.0f)
                    curveTo(19.2669f, 15.3016f, 19.2272f, 15.6362f, 19.286f, 15.9606f)
                    curveTo(19.3448f, 16.285f, 19.4995f, 16.5843f, 19.73f, 16.82f)
                    lineTo(19.79f, 16.88f)
                    curveTo(19.976f, 17.0657f, 20.1235f, 17.2863f, 20.2241f, 17.5291f)
                    curveTo(20.3248f, 17.7719f, 20.3766f, 18.0322f, 20.3766f, 18.295f)
                    curveTo(20.3766f, 18.5578f, 20.3248f, 18.8181f, 20.2241f, 19.0609f)
                    curveTo(20.1235f, 19.3037f, 19.976f, 19.5243f, 19.79f, 19.71f)
                    curveTo(19.6043f, 19.896f, 19.3837f, 20.0435f, 19.1409f, 20.1441f)
                    curveTo(18.8981f, 20.2448f, 18.6378f, 20.2966f, 18.375f, 20.2966f)
                    curveTo(18.1122f, 20.2966f, 17.8519f, 20.2448f, 17.6091f, 20.1441f)
                    curveTo(17.3663f, 20.0435f, 17.1457f, 19.896f, 16.96f, 19.71f)
                    lineTo(16.9f, 19.65f)
                    curveTo(16.6643f, 19.4195f, 16.365f, 19.2648f, 16.0406f, 19.206f)
                    curveTo(15.7162f, 19.1472f, 15.3816f, 19.1869f, 15.08f, 19.32f)
                    curveTo(14.7842f, 19.4468f, 14.532f, 19.6572f, 14.3543f, 19.9255f)
                    curveTo(14.1766f, 20.1938f, 14.0813f, 20.5082f, 14.08f, 20.83f)
                    verticalLineTo(21.0f)
                    curveTo(14.08f, 21.5304f, 13.8693f, 22.0391f, 13.4942f, 22.4142f)
                    curveTo(13.1191f, 22.7893f, 12.6104f, 23.0f, 12.08f, 23.0f)
                    curveTo(11.5496f, 23.0f, 11.0409f, 22.7893f, 10.6658f, 22.4142f)
                    curveTo(10.2907f, 22.0391f, 10.08f, 21.5304f, 10.08f, 21.0f)
                    verticalLineTo(20.91f)
                    curveTo(10.0723f, 20.579f, 9.9651f, 20.258f, 9.7725f, 19.9887f)
                    curveTo(9.5799f, 19.7194f, 9.3107f, 19.5143f, 9.0f, 19.4f)
                    curveTo(8.6984f, 19.2669f, 8.3638f, 19.2272f, 8.0394f, 19.286f)
                    curveTo(7.715f, 19.3448f, 7.4157f, 19.4995f, 7.18f, 19.73f)
                    lineTo(7.12f, 19.79f)
                    curveTo(6.9342f, 19.976f, 6.7137f, 20.1235f, 6.4709f, 20.2241f)
                    curveTo(6.2281f, 20.3248f, 5.9678f, 20.3766f, 5.705f, 20.3766f)
                    curveTo(5.4422f, 20.3766f, 5.1819f, 20.3248f, 4.9391f, 20.2241f)
                    curveTo(4.6963f, 20.1235f, 4.4757f, 19.976f, 4.29f, 19.79f)
                    curveTo(4.1041f, 19.6043f, 3.9565f, 19.3837f, 3.8559f, 19.1409f)
                    curveTo(3.7552f, 18.8981f, 3.7034f, 18.6378f, 3.7034f, 18.375f)
                    curveTo(3.7034f, 18.1122f, 3.7552f, 17.8519f, 3.8559f, 17.6091f)
                    curveTo(3.9565f, 17.3663f, 4.1041f, 17.1457f, 4.29f, 16.96f)
                    lineTo(4.35f, 16.9f)
                    curveTo(4.5805f, 16.6643f, 4.7352f, 16.365f, 4.794f, 16.0406f)
                    curveTo(4.8528f, 15.7162f, 4.8131f, 15.3816f, 4.68f, 15.08f)
                    curveTo(4.5532f, 14.7842f, 4.3428f, 14.532f, 4.0745f, 14.3543f)
                    curveTo(3.8062f, 14.1766f, 3.4918f, 14.0813f, 3.17f, 14.08f)
                    horizontalLineTo(3.0f)
                    curveTo(2.4696f, 14.08f, 1.9609f, 13.8693f, 1.5858f, 13.4942f)
                    curveTo(1.2107f, 13.1191f, 1.0f, 12.6104f, 1.0f, 12.08f)
                    curveTo(1.0f, 11.5496f, 1.2107f, 11.0409f, 1.5858f, 10.6658f)
                    curveTo(1.9609f, 10.2907f, 2.4696f, 10.08f, 3.0f, 10.08f)
                    horizontalLineTo(3.09f)
                    curveTo(3.421f, 10.0723f, 3.742f, 9.9651f, 4.0113f, 9.7725f)
                    curveTo(4.2806f, 9.5799f, 4.4857f, 9.3107f, 4.6f, 9.0f)
                    curveTo(4.7331f, 8.6984f, 4.7728f, 8.3638f, 4.714f, 8.0394f)
                    curveTo(4.6552f, 7.715f, 4.5005f, 7.4157f, 4.27f, 7.18f)
                    lineTo(4.21f, 7.12f)
                    curveTo(4.0241f, 6.9342f, 3.8765f, 6.7137f, 3.7759f, 6.4709f)
                    curveTo(3.6752f, 6.2281f, 3.6234f, 5.9678f, 3.6234f, 5.705f)
                    curveTo(3.6234f, 5.4422f, 3.6752f, 5.1819f, 3.7759f, 4.9391f)
                    curveTo(3.8765f, 4.6963f, 4.0241f, 4.4757f, 4.21f, 4.29f)
                    curveTo(4.3958f, 4.1041f, 4.6163f, 3.9565f, 4.8591f, 3.8559f)
                    curveTo(5.1019f, 3.7552f, 5.3622f, 3.7034f, 5.625f, 3.7034f)
                    curveTo(5.8878f, 3.7034f, 6.1481f, 3.7552f, 6.3909f, 3.8559f)
                    curveTo(6.6337f, 3.9565f, 6.8542f, 4.1041f, 7.04f, 4.29f)
                    lineTo(7.1f, 4.35f)
                    curveTo(7.3357f, 4.5805f, 7.635f, 4.7352f, 7.9594f, 4.794f)
                    curveTo(8.2838f, 4.8528f, 8.6184f, 4.8131f, 8.92f, 4.68f)
                    horizontalLineTo(9.0f)
                    curveTo(9.2958f, 4.5532f, 9.548f, 4.3428f, 9.7257f, 4.0745f)
                    curveTo(9.9034f, 3.8062f, 9.9987f, 3.4918f, 10.0f, 3.17f)
                    verticalLineTo(3.0f)
                    curveTo(10.0f, 2.4696f, 10.2107f, 1.9609f, 10.5858f, 1.5858f)
                    curveTo(10.9609f, 1.2107f, 11.4696f, 1.0f, 12.0f, 1.0f)
                    curveTo(12.5304f, 1.0f, 13.0391f, 1.2107f, 13.4142f, 1.5858f)
                    curveTo(13.7893f, 1.9609f, 14.0f, 2.4696f, 14.0f, 3.0f)
                    verticalLineTo(3.09f)
                    curveTo(14.0013f, 3.4118f, 14.0966f, 3.7262f, 14.2743f, 3.9945f)
                    curveTo(14.452f, 4.2628f, 14.7042f, 4.4732f, 15.0f, 4.6f)
                    curveTo(15.3016f, 4.7331f, 15.6362f, 4.7728f, 15.9606f, 4.714f)
                    curveTo(16.285f, 4.6552f, 16.5843f, 4.5005f, 16.82f, 4.27f)
                    lineTo(16.88f, 4.21f)
                    curveTo(17.0657f, 4.0241f, 17.2863f, 3.8765f, 17.5291f, 3.7759f)
                    curveTo(17.7719f, 3.6752f, 18.0322f, 3.6234f, 18.295f, 3.6234f)
                    curveTo(18.5578f, 3.6234f, 18.8181f, 3.6752f, 19.0609f, 3.7759f)
                    curveTo(19.3037f, 3.8765f, 19.5243f, 4.0241f, 19.71f, 4.21f)
                    curveTo(19.896f, 4.3958f, 20.0435f, 4.6163f, 20.1441f, 4.8591f)
                    curveTo(20.2448f, 5.1019f, 20.2966f, 5.3622f, 20.2966f, 5.625f)
                    curveTo(20.2966f, 5.8878f, 20.2448f, 6.1481f, 20.1441f, 6.3909f)
                    curveTo(20.0435f, 6.6337f, 19.896f, 6.8542f, 19.71f, 7.04f)
                    lineTo(19.65f, 7.1f)
                    curveTo(19.4195f, 7.3357f, 19.2648f, 7.635f, 19.206f, 7.9594f)
                    curveTo(19.1472f, 8.2838f, 19.1869f, 8.6184f, 19.32f, 8.92f)
                    verticalLineTo(9.0f)
                    curveTo(19.4468f, 9.2958f, 19.6572f, 9.548f, 19.9255f, 9.7257f)
                    curveTo(20.1938f, 9.9034f, 20.5082f, 9.9987f, 20.83f, 10.0f)
                    horizontalLineTo(21.0f)
                    curveTo(21.5304f, 10.0f, 22.0391f, 10.2107f, 22.4142f, 10.5858f)
                    curveTo(22.7893f, 10.9609f, 23.0f, 11.4696f, 23.0f, 12.0f)
                    curveTo(23.0f, 12.5304f, 22.7893f, 13.0391f, 22.4142f, 13.4142f)
                    curveTo(22.0391f, 13.7893f, 21.5304f, 14.0f, 21.0f, 14.0f)
                    horizontalLineTo(20.91f)
                    curveTo(20.5882f, 14.0013f, 20.2738f, 14.0966f, 20.0055f, 14.2743f)
                    curveTo(19.7372f, 14.452f, 19.5268f, 14.7042f, 19.4f, 15.0f)
                    close()
                }
            }
        }
        .build()
        return _settings!!
    }

private var _settings: ImageVector? = null
