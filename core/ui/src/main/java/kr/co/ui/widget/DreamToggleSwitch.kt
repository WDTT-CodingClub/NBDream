package kr.co.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors

private const val TOGGLE_SWITCH_RADIUS = 25
private const val TOGGLE_SWITCH_HEIGHT = 30
private const val TOGGLE_SWITCH_WIDTH = 66
private const val TOGGLE_SWITCH_HANDLE_SIZE = 20

@Composable
fun DreamToggleSwitch(
    toggleState: Boolean,
    onToggleStateChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    val horizontalBias by animateFloatAsState(
        targetValue = if (toggleState) 1f else -1f, label = ""
    )

    Box(
        modifier = modifier
            .size(
                height = TOGGLE_SWITCH_HEIGHT.dp,
                width = TOGGLE_SWITCH_WIDTH.dp
            )
            .clip(RoundedCornerShape(TOGGLE_SWITCH_RADIUS.dp))
            .background(
                if (toggleState) MaterialTheme.colors.primary else Color.Gray
            )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = Paddings.small)
                .align(BiasAlignment(horizontalBias, 0f))
                .size(TOGGLE_SWITCH_HANDLE_SIZE.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onToggleStateChange() }
        )
    }
}

@Preview
@Composable
private fun DreamToggleSwitchPreview() {
    var state by remember { mutableStateOf(false) }
    DreamToggleSwitch(
        toggleState = state,
        onToggleStateChange = { state = !state }
    )
}