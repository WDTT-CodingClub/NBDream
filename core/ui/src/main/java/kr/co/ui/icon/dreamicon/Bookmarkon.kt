import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kr.co.ui.icon.DreamIcon

private var _Bookmarkon: ImageVector? = null

public val DreamIcon.Bookmarkon: ImageVector
	get() {
		if (_Bookmarkon != null) {
			return _Bookmarkon!!
		}
		_Bookmarkon = ImageVector.Builder(
			name = "Bookmarkon",
			defaultWidth = 24.dp,
			defaultHeight = 24.dp,
			viewportWidth = 24f,
			viewportHeight = 24f
		).apply {
			path(
				fill = SolidColor(Color(0xFF594D42)),
				fillAlpha = 1.0f,
				stroke = null,
				strokeAlpha = 1.0f,
				strokeLineWidth = 1.0f,
				strokeLineCap = StrokeCap.Butt,
				strokeLineJoin = StrokeJoin.Miter,
				strokeLineMiter = 1.0f,
				pathFillType = PathFillType.EvenOdd
			) {
				moveTo(20.25f, 4f)
				verticalLineTo(21.066f)
				curveTo(20.25f, 21.527f, 19.996f, 21.95f, 19.59f, 22.168f)
				curveTo(19.183f, 22.385f, 18.69f, 22.362f, 18.307f, 22.106f)
				lineTo(12.693f, 18.364f)
				curveTo(12.274f, 18.084f, 11.726f, 18.084f, 11.307f, 18.364f)
				lineTo(5.693f, 22.106f)
				curveTo(5.31f, 22.362f, 4.817f, 22.385f, 4.41f, 22.168f)
				curveTo(4.004f, 21.95f, 3.75f, 21.527f, 3.75f, 21.066f)
				verticalLineTo(4f)
				curveTo(3.75f, 3.271f, 4.04f, 2.571f, 4.555f, 2.055f)
				curveTo(5.071f, 1.54f, 5.771f, 1.25f, 6.5f, 1.25f)
				horizontalLineTo(17.5f)
				curveTo(18.229f, 1.25f, 18.929f, 1.54f, 19.445f, 2.055f)
				curveTo(19.96f, 2.571f, 20.25f, 3.271f, 20.25f, 4f)
				close()
			}
		}.build()
		return _Bookmarkon!!
	}

