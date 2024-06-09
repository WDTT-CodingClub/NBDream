package kr.co.wdtt.nbdream.ui.main.calendar.common

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kr.co.wdtt.nbdream.R
import kr.co.wdtt.nbdream.domain.entity.WeatherForecastEntity
import kr.co.wdtt.nbdream.ui.icon.dreamicon.ArrowLeft
import kr.co.wdtt.nbdream.ui.icon.dreamicon.DreamIcon
import kr.co.wdtt.nbdream.ui.theme.colors
import kr.co.wdtt.nbdream.ui.theme.typo
import java.time.LocalDate


private const val CROP_COLOR_SHAPE_SIZE = 8

@Composable
fun CalendarBaseTopBar(
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
fun CalendarHorizontalDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier,
        color = Color.LightGray
    )
}

@Composable
fun CalendarDatePicker(
    date: LocalDate,
    onDatePick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
){
    //TODO Date Picker
}


@Composable
fun CalendarWeather(
    weatherForecast: WeatherForecastEntity,
    modifier: Modifier = Modifier
) {
    val weatherForcastStr = remember {
        with(weatherForecast) {
            "${maxTemperature}/${minTemperature} ${precipitation} ${weather}"
        }
    }

    Row(modifier = modifier) {
        // TODO 하늘 별 아이콘 표시
        Text(
            modifier = Modifier,
            text = weatherForcastStr,
            style = MaterialTheme.typo.bodyM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
fun CalendarCategoryIndicator(
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
