package kr.co.ui.ext

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    borerRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    spread: Dp = 0.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {  canvas ->
            val paint = Paint()
            val nativePaint = paint.asFrameworkPaint()

            nativePaint.maskFilter = BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
            nativePaint.color = color.toArgb()

            canvas.drawRoundRect(
                left = (0f - spread.toPx()) + offsetX.toPx(),
                top = (0f - spread.toPx()) + offsetY.toPx(),
                right = (this.size.width + spread.toPx()) - offsetX.toPx(),
                bottom = (this.size.height + spread.toPx()) - offsetY.toPx(),
                radiusX = borerRadius.toPx(),
                radiusY = borerRadius.toPx(),
                paint = paint
            )
        }
    }
)

fun Modifier.scaffoldBackground(
    scaffoldPadding: PaddingValues,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp)
) = this
    .fillMaxSize()
    .padding(scaffoldPadding)
    .padding(padding)

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier
): Modifier = if (condition) then(ifTrue(Modifier)) else this