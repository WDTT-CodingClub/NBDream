package kr.co.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kr.co.ui.ext.conditional
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kotlin.math.roundToInt

private val ThumbSize = 16.dp
private const val SwitchWidth = 48
private const val SwitchHeight = 24
private val ThumbDiameter = 24.0.dp
private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

@Composable
fun DreamSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.green4,
    trackColor: Color = MaterialTheme.colors.gray8,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val thumbPaddingStart = (SwitchHeight.dp - ThumbDiameter) / 2
    val minBound = with(LocalDensity.current) { thumbPaddingStart.toPx() + SwitchHeight/2 }
    val maxBound = with(LocalDensity.current) { (SwitchWidth.dp - ThumbSize).toPx() - SwitchHeight/2}
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val targetValue = valueToOffset(checked)
    val offset = remember { Animatable(targetValue) }
    val scope = rememberCoroutineScope()

    SideEffect {
        // min bound might have changed if the icon is only rendered in checked state.
        offset.updateBounds(lowerBound = minBound)
    }

    DisposableEffect(checked) {
        if (offset.targetValue != targetValue) {
            scope.launch {
                offset.animateTo(targetValue, AnimationSpec)
            }
        }
        onDispose { }
    }

    val toggleableModifier =
            Modifier.toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = true,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )

    Box(
        modifier
            .minimumInteractiveComponentSize()
            .then(toggleableModifier)
            .size(SwitchWidth.dp, SwitchHeight.dp)
            .background(trackColor, CircleShape)
            .conditional(
                condition = checked,
            ){
                background(color, CircleShape)
            }
            .pointerInput(checked) {
                detectTapGestures {
                    onCheckedChange.invoke(!checked)
                }
            }
    ) {
        val thumbOffset = offset.value
        Box(
            Modifier
                .offset { IntOffset(thumbOffset.roundToInt(), SwitchHeight/2) }
                .size(ThumbSize)
                .background(Color.White, CircleShape)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val (checked, onCheckedChange) = remember { mutableStateOf(false) }
    NBDreamTheme {
        DreamSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}