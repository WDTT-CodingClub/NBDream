package kr.co.main.calendar.common

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import kr.co.common.util.toDateString
import kr.co.main.calendar.model.WeatherForecastModel
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.ArrowLeft
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate

private const val SKY_ICON_SIZE = 20

private const val CROP_COLOR_SHAPE_SIZE = 8

@Composable
internal fun CalendarBaseTopBar(
    @StringRes titleId: Int,
    @StringRes rightLabelId: Int,
    onBackClick: () -> Unit,
    onRightLabelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onBackClick() },
            imageVector = DreamIcon.ArrowLeft,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = titleId),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onRightLabelClick() },
            text = stringResource(id = rightLabelId),
            style = MaterialTheme.typo.header2M,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
internal fun CalendarHorizontalDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier,
        color = Color.LightGray
    )
}

@Composable
internal fun CalendarDatePicker(
    date: LocalDate,
    onDatePick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable {
                    //TODO Date Picker 띄우기 - 수빈님 PR 올리시면 참고
                },
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier,
                text = date.toDateString(),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.text1
            )
            Icon(
                modifier = Modifier,
                imageVector = DreamIcon.Edit, // TODO 캘린더 아이콘으로 바꾸기
                contentDescription = ""
            )
        }
    }
}


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
                .size(SKY_ICON_SIZE.dp),
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
            .size(CROP_COLOR_SHAPE_SIZE.dp)
            .clip(CircleShape)
            .background(Color(categoryColor)),
    )
}

