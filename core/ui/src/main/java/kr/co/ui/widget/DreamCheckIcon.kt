package kr.co.ui.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import kr.co.ui.ext.IconDefaults
import kr.co.ui.ext.VectorColors
import kr.co.ui.ext.vectorColors
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors

@Composable
fun DreamCheckIcon(
    state: Boolean,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: VectorColors = IconDefaults.vectorColors()
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier,
            imageVector = if (state) rightIcon else leftIcon,
            contentDescription = contentDescription,
            tint = if (state) colors.pressed else colors.default
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val (selected, setSelected) = remember { mutableStateOf<Int?>(null) }
    NBDreamTheme {
        DreamCheckIcon(
            state = selected == 0,
            leftIcon = Icons.Filled.AccountCircle,
            rightIcon = Icons.Filled.CheckCircle,
            contentDescription = "check reason",
            onClick = { setSelected(if (selected == 0) null else 0) },
            colors = IconDefaults.vectorColors(
                default = MaterialTheme.colors.white,
                pressed = MaterialTheme.colors.primary
            )
        )
    }
}