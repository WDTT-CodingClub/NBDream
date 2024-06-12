package kr.co.main.calendar.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kr.co.ui.theme.colors

@Composable
internal fun CalendarBaseFab(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = Color.White,
) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        containerColor = containerColor,
        contentColor = contentColor,
        onClick = { onClick() }
    ) {
        Icon(
            modifier = Modifier.size(CalendarDesignToken.FAB_ICON_SIZE.dp),
            imageVector = imageVector,
            contentDescription = ""
        )
    }
}