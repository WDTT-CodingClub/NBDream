package kr.co.main.calendar.ui.common.innerCalendar

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kr.co.common.util.iterator
import kr.co.main.R
import kr.co.main.calendar.model.HolidayModel
import kr.co.main.calendar.ui.CalendarDesignToken
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
internal fun InnerCalendar(
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    holidays: List<HolidayModel> = emptyList(),
    items: List<InnerCalendarItem> = emptyList()
) {
    val innerCalendarStateHolder = rememberInnerCalendarStateHolder(
        calendarYear, calendarMonth, selectedDate, holidays, items
    )

    StatelessInnerCalendar(
        modifier = modifier,
        onSelectDate = onSelectDate,
        innerCalendarStateHolder = innerCalendarStateHolder
    )
}

@Composable
private fun rememberInnerCalendarStateHolder(
    year: Int,
    month: Int,
    selectedDate: LocalDate,
    holidays: List<HolidayModel>,
    items: List<InnerCalendarItem>
) = remember {
    InnerCalendarStateHolder(year, month, selectedDate, holidays, items)
}


@Composable
private fun StatelessInnerCalendar(
    innerCalendarStateHolder: InnerCalendarStateHolder,
    onSelectDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val monthWeekRange = remember(innerCalendarStateHolder.year, innerCalendarStateHolder.month) {
        innerCalendarStateHolder.getMonthWeekRange()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.xlarge)
    ) {
        InnerCalendarDayOfWeekRow(
            modifier = Modifier.fillMaxWidth()
        )
        for (weekNum in monthWeekRange.start..monthWeekRange.endInclusive) {
            val weekDateRange = innerCalendarStateHolder.getWeekDateRange(weekNum)
            Box{
                InnerCalendarDateRow(
                    calendarMonth = innerCalendarStateHolder.month,
                    selectedDate = innerCalendarStateHolder.selectedDate,
                    weekDateRange = weekDateRange,
                    holidays = innerCalendarStateHolder.holidays
                )
                InnerCalendarItemRow(
                    onSelectDate = onSelectDate,
                    weekDateRange = weekDateRange,
                    items = innerCalendarStateHolder.items
                )
            }
        }
    }
}

@Composable
private fun InnerCalendarDayOfWeekRow(
    modifier: Modifier = Modifier
) {
    val dayOfWeekIds = remember {
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
    Row(modifier = modifier) {
        dayOfWeekIds.forEachIndexed { index, id ->
            InnerCalendarDayOfWeekItem(
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
private fun InnerCalendarDayOfWeekItem(
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

@Composable
private fun InnerCalendarDateRow(
    calendarMonth: Int,
    selectedDate: LocalDate,
    weekDateRange: ClosedRange<LocalDate>,
    holidays: List<HolidayModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (date in weekDateRange) {
            InnerCalendarDateItem(
                modifier = Modifier
                    .weight(1f)
                    .alpha( if (date.monthValue == calendarMonth) 1f else 0.3f),
                date = date,
                isSelected = (date == selectedDate),
                isHoliday = holidays.any { it.date == date } or (date.dayOfWeek == DayOfWeek.SUNDAY)
            )
        }
    }
}

@Composable
private fun InnerCalendarDateItem(
    date: LocalDate,
    isSelected: Boolean,
    isHoliday: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor =
        if (isHoliday) MaterialTheme.colors.red1
        else if (isSelected) Color.White
        else MaterialTheme.colors.text1

    val backgroundColor =
        if (isSelected) Color.Black else Color.Transparent

    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(Paddings.small),
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typo.bodyR,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun InnerCalendarItemRow(
    onSelectDate: (LocalDate) -> Unit,
    weekDateRange: ClosedRange<LocalDate>,
    items: List<InnerCalendarItem>,
    modifier: Modifier = Modifier
) {
    val contents: @Composable () -> Unit = {
        filterAndSortItems(items, weekDateRange)
    }
}

@Composable
private fun InnerCalendarItem(
    data: InnerCalendarItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(CalendarDesignToken.FARM_WORK_ITEM_HEIGHT.dp)
            .clip(shape = RoundedCornerShape(CalendarDesignToken.FARM_WORK_ITEM_CORNER_RADIUS.dp))
            .background(color = MaterialTheme.colors.gray8),
    )
}



