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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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

private const val ROUNDED_CORNER_RADIUS = 12

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
    onDateInput: (LocalDate) -> Unit,
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
internal fun CalendarUnderLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: @Composable () -> Unit = {},
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = maxLines,
        textStyle = TextStyle.Default.copy(
            textAlign = textAlign
        ),
        keyboardOptions = keyboardOptions
    ) { innerTextField ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (value.isEmpty()) placeHolder()
            innerTextField()
            HorizontalDivider()
        }
    }
}

@Composable
internal fun CalendarContainerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: @Composable () -> Unit = {},
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = maxLines,
        textStyle = TextStyle.Default.copy(
            textAlign = textAlign
        ),
        keyboardOptions = keyboardOptions
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(ROUNDED_CORNER_RADIUS.dp))
                .background(Color.LightGray),
        ) {
            Box(modifier = Modifier.padding(Paddings.medium)) {
                if (value.isEmpty()) placeHolder()
                innerTextField()
            }
        }
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

