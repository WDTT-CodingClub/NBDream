package kr.co.main.calendar.ui.common.inner_calendar

import android.util.Range
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import kr.co.main.calendar.model.filterAndSortHolidays
import kr.co.main.calendar.ui.common.CalendarDesignToken
import kr.co.main.calendar.ui.common.content.CalendarContent
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate

internal data class InnerCalendarData(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val calendarContent: CalendarContent
)

@Composable
internal fun InnerCalendar(
    calendarYear: Int,
    calendarMonth: Int,
    modifier: Modifier = Modifier,
    selectedDate: LocalDate = LocalDate.now(),
    holidayList: List<HolidayModel> = emptyList(),
    dataList: List<InnerCalendarData> = emptyList()
) {
    val stateHolder = rememberInnerCalendarStateHolder(calendarYear, calendarMonth)

    InnerCalendarContent(
        modifier = modifier,
        calendarYear = calendarYear,
        calendarMonth = calendarMonth,
        startWeekNum = stateHolder.startWeekNumber,
        endWeekNum = stateHolder.endWeekNumber,
        getWeekRange = stateHolder::getWeekRange,
        holidayList = holidayList,
        dataList = dataList
    )
}

@Composable
private fun rememberInnerCalendarStateHolder(year: Int, month: Int) = remember {
    InnerCalendarStateHolder(year, month)
}


@Composable
private fun InnerCalendarContent(
    calendarYear: Int,
    calendarMonth: Int,
    startWeekNum: Int,
    endWeekNum: Int,
    getWeekRange: (Int, Int) -> Pair<LocalDate, LocalDate>,
    holidayList: List<HolidayModel>,
    dataList: List<InnerCalendarData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Paddings.xlarge)
    ) {
        InnerCalendarDayOfWeekRow(
            modifier = Modifier.fillMaxWidth()
        )

        for (weekNum in startWeekNum..endWeekNum) {
            val (weekStartDate, weekEndDate) = getWeekRange(calendarYear, weekNum)
            InnerCalendarRow(
                calendarMonth = calendarMonth,
                weekStartDate = weekStartDate,
                weekEndDate = weekEndDate,
                holidayList = filterAndSortHolidays(
                    holidays = holidayList,
                    dateRange = Range(weekStartDate, weekEndDate)
                ),
                dataList = filterAndSortDatas(
                    datas= dataList,
                    dateRange = Range(weekStartDate, weekEndDate)
                )
            )
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
internal fun InnerCalendarRow(
    calendarMonth: Int,
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    holidayList: List<HolidayModel>,
    dataList: List<InnerCalendarData>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        InnerCalendarDateRow(
            calendarMonth = calendarMonth,
            weekStartDate = weekStartDate,
            weekEndDate = weekEndDate,
            holidayList = holidayList
        )
        InnerCalendarDataRow(
            weekStartDate = weekStartDate,
            weekEndDate = weekEndDate,
            dataList = dataList
        )
    }
}

@Composable
private fun InnerCalendarDateRow(
    calendarMonth: Int,
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    holidayList: List<HolidayModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (date in weekStartDate..weekEndDate) {
            InnerCalendarDateItem(
                modifier = Modifier
                    .weight(1f)
                    .alpha(
                        if (date.monthValue == calendarMonth) 1f else 0.5f
                    ),
                date = date,
                isHoliday = holidayList.any { it.date == date } or (date.dayOfWeek == DayOfWeek.SUNDAY)
            )
        }
    }
}

@Composable
private fun InnerCalendarDateItem(
    date: LocalDate,
    isHoliday: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor =
        if (isHoliday) MaterialTheme.colors.red1
        else MaterialTheme.colors.text1

    Text(
        modifier = modifier,
        text = date.dayOfMonth.toString(),
        style = MaterialTheme.typo.bodyR,
        color = textColor,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun InnerCalendarDataRow(
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    dataList: List<InnerCalendarData>,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun InnerCalendarDataItem(
    data: InnerCalendarData,
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

private fun filterAndSortDatas(
    datas: List<InnerCalendarData>,
    dateRange: Range<LocalDate>
) = datas
    .filter {
        (it.startDate in dateRange) or (it.endDate in dateRange)
    }
    .sortedWith(compareBy({ it.startDate }, { it.endDate }))


