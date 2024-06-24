package kr.co.main.calendar.ui.common

import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kr.co.main.calendar.ui.CalendarDesignToken

@Composable
internal fun CalendarCategoryIndicator(
    @ColorInt categoryColor: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(CalendarDesignToken.CROP_COLOR_SHAPE_SIZE.dp)
            .clip(CircleShape)
            .background(Color(categoryColor)),
    )
}