package kr.co.main.calendar.ui.common

import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kr.co.main.calendar.common.CalendarDesignToken
import kr.co.main.calendar.model.WeatherForecastModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun CalendarWeather(
    weatherForecast: WeatherForecastModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = Paddings.medium)
                .size(CalendarDesignToken.SKY_ICON_SIZE.dp),
            imageVector = weatherForecast.sky.icon,
            contentDescription = ""
        )
        Text(
            modifier = Modifier,
            text = with(weatherForecast) {
                "${maxTemp}/${minTemp} $precipitation " + stringResource(id = sky.labelId)
            },
            style = MaterialTheme.typo.bodyM,
            color = MaterialTheme.colors.text1
        )
    }
}

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