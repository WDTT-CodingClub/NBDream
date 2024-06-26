package kr.co.main.calendar.screen.calendarScreen.calendar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kr.co.main.R
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

private val dayOfWeekIds by lazy {
    listOf(
        R.string.feature_main_calendar_day_of_week_sun,
        R.string.feature_main_calendar_day_of_week_mon,
        R.string.feature_main_calendar_day_of_week_tue,
        R.string.feature_main_calendar_day_of_week_wed,
        R.string.feature_main_calendar_day_of_week_thu,
        R.string.feature_main_calendar_day_of_week_fri,
        R.string.feature_main_calendar_day_of_week_sat,
    )
}

@Composable
internal fun CalendarDayOfWeekRow(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        dayOfWeekIds.forEachIndexed { index, id ->
            CalendarDayOfWeekItem(
                modifier = Modifier.weight(1f),
                dayOfWeekId = id,
                dayOfWeekColor =
                if (index == 0) MaterialTheme.colors.red1
                else MaterialTheme.colors.text2
            )
        }
    }
}

@Composable
private fun CalendarDayOfWeekItem(
    @StringRes dayOfWeekId: Int,
    modifier: Modifier = Modifier,
    dayOfWeekColor: Color = MaterialTheme.colors.text1
) {
    Text(
        modifier = modifier,
        text = stringResource(id = dayOfWeekId),
        style = MaterialTheme.typo.bodyR,
        color = dayOfWeekColor,
        textAlign = TextAlign.Center
    )
}