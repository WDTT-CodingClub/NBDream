package kr.co.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

object IconDefaults

@Composable
fun IconDefaults.vectorColors(
    default: Color = Color.Unspecified,
    pressed: Color = Color.Unspecified,
    disabled: Color = Color.Unspecified,
) = VectorColors(
    default = default,
    pressed = pressed,
    disabled = disabled,
)

@Immutable
class VectorColors(
    val default: Color,
    val pressed: Color,
    val disabled: Color,
) {
    fun copy(
        default: Color = this.default,
        pressed: Color = this.pressed,
        disabled: Color = this.disabled,
    ) = VectorColors(
        default = default,
        pressed = pressed,
        disabled = disabled,
    )
}