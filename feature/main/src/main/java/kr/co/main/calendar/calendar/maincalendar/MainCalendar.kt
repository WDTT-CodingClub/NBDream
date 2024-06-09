package kr.co.main.calendar.calendar.maincalendar

import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Constraints
import kr.co.common.util.iterator
import kr.co.domain.entity.HolidayEntity
import kr.co.main.R
import kr.co.main.calendar.common.CalendarCategoryIndicator
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.wdtt.nbdream.ui.main.calendar.providers.FakeMainCalendarDataProvider
import java.time.DayOfWeek
import java.time.LocalDate

internal data class MainCalendarData(
    val year: Int,
    val month: Int,
    val crop: CropModel,
    val holidays: List<HolidayEntity>,
    val schedules: List<ScheduleModel>,
    val diaries: List<DiaryModel>
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCalendar(
    year: Int,
    month: Int,
    crop: CropModel,
    holidays: List<HolidayEntity>,
    schedules: List<ScheduleModel>,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    val scheduleCalendarStateHolder = rememberScheduleCalendarStateHolder(year, month)

    Column(modifier = modifier.fillMaxWidth()) {
        CategoryIndicatorList(
            modifier = Modifier.padding(vertical = Paddings.medium),
            crop = crop
        )
        DayOfWeekHeader()
        MainCalendarContent(
            year = year,
            startWeekNum = scheduleCalendarStateHolder.startWeekNumber,
            endWeekNum = scheduleCalendarStateHolder.endWeekNumber,
            getWeekRange = scheduleCalendarStateHolder::getWeekRange,
            holidays = holidays,
            schedules = schedules,
            diaries = diaries
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun rememberScheduleCalendarStateHolder(year: Int, month: Int) = remember {
    MainCalendarStateHolder(year, month)
}

@Composable
private fun CategoryIndicatorList(
    crop: CropModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIndicatorListItem(
            cropNameId = crop.nameId,
            cropColor = crop.color
        )
        CategoryIndicatorListItem(
            cropNameId = R.string.feature_main_calendar_category_all,
            cropColor = Color.Gray.toArgb()
        )
    }
}

@Composable
private fun CategoryIndicatorListItem(
    @StringRes cropNameId: Int,
    @ColorInt cropColor: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarCategoryIndicator(
            modifier = Modifier.padding(end = Paddings.small),
            categoryColor = cropColor
        )
        Text(
            text = stringResource(id = cropNameId),
            style = MaterialTheme.typo.labelM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
private fun DayOfWeekHeader(
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
    Row(modifier = modifier.fillMaxWidth()) {
        dayOfWeekIds.forEachIndexed { index, id ->
            DayOfWeekItem(
                modifier = Modifier.weight(1f),
                dayOfWeekId = id,
                dayOfWeekColor = if (index == 0) MaterialTheme.colors.red1 else MaterialTheme.colors.text1
            )
        }
    }
}

@Composable
private fun DayOfWeekItem(
    @StringRes dayOfWeekId: Int,
    dayOfWeekColor: Color = MaterialTheme.colors.text1,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = dayOfWeekId),
        style = MaterialTheme.typo.header2SB,
        color = dayOfWeekColor,
        textAlign = TextAlign.Center
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainCalendarContent(
    year: Int,
    startWeekNum: Int,
    endWeekNum: Int,
    getWeekRange: (Int, Int) -> Pair<LocalDate, LocalDate>,
    holidays: List<HolidayEntity>,
    schedules: List<ScheduleModel>,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        for (weekNum in startWeekNum..endWeekNum) {
            val (weekStartDate, weekEndDate) = getWeekRange(year, weekNum)

            MainCalendarDateRow(
                weekStartDate = weekStartDate,
                weekEndDate = weekEndDate,
                holidays = holidays
            )
            MainCalendarHolidayRow(
                weekStartDate = weekStartDate,
                weekEndDate = weekEndDate,
                holidays = holidays.sortedBy { it.date }
            )
            MainCalendarScheduleRow(
                weekStartDate = weekStartDate,
                weekEndDate = weekEndDate,
                schedules = schedules.sortedWith(compareBy({ it.startDate }, { it.endDate }))
            )
            MainCalendarDiaryRow(
                weekStartDate = weekStartDate,
                weekEndDate = weekEndDate,
                diaries = diaries.sortedBy { it.registerDate }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainCalendarDateRow(
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    holidays: List<HolidayEntity>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        for (date in weekStartDate..weekEndDate) {
            val isHoliday = (
                    holidays.any { (it.date == date) && (it.isHoliday) } or
                            (date.dayOfWeek == DayOfWeek.SUNDAY)
                    )

            Text(
                modifier = Modifier.weight(1f),
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typo.labelSB,
                color = if (isHoliday) MaterialTheme.colors.red1 else MaterialTheme.colors.text1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun MainCalendarPreview(
    @PreviewParameter(FakeMainCalendarDataProvider::class) mainCalendarData: MainCalendarData
) {
    Surface {
        MainCalendar(
            year = mainCalendarData.year,
            month = mainCalendarData.month,
            crop = mainCalendarData.crop,
            holidays = mainCalendarData.holidays,
            schedules = mainCalendarData.schedules,
            diaries = mainCalendarData.diaries
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun getXPosition(dayOfWeek: DayOfWeek, constraints: Constraints) =
    when (dayOfWeek) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> constraints.maxWidth / 7
        DayOfWeek.TUESDAY -> constraints.maxWidth / 7 * 2
        DayOfWeek.WEDNESDAY -> constraints.maxWidth / 7 * 3
        DayOfWeek.THURSDAY -> constraints.maxWidth / 7 * 4
        DayOfWeek.FRIDAY -> constraints.maxWidth / 7 * 5
        DayOfWeek.SATURDAY -> constraints.maxWidth / 7 * 6
    }

