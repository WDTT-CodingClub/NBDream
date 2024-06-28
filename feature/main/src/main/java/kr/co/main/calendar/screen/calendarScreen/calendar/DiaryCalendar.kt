package kr.co.main.calendar.screen.calendarScreen.calendar

import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.co.common.util.LocalDateUtil
import kr.co.common.util.iterator
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.type.CropModelColorType
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate

private class DiaryCalendarStateHolder(
    @ColorInt val cropColor: Int,
    val year: Int,
    val month: Int,
    val selectedDate: LocalDate = LocalDate.now(),
    val onDateSelect: (LocalDate) -> Unit,
    val holidays: List<HolidayModel> = emptyList(),
    val diaries: List<DiaryModel> = emptyList()
) {
    val startWeekNum = LocalDateUtil.getStartWeekNumber(year, month)
    val endWeekNum = LocalDateUtil.getEndWeekNumber(year, month)
}

@Composable
internal fun DiaryCalendar(
    @ColorInt cropColor: Int,
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    val stateHolder = rememberDiaryCalendarStateHolder(
        cropColor,
        calendarYear,
        calendarMonth,
        selectedDate,
        onDateSelect,
        holidays,
        diaries
    )

    StatelessDiaryCalendar(
        modifier = modifier,
        stateHolder = stateHolder,
    )
}

@Composable
private fun rememberDiaryCalendarStateHolder(
    @ColorInt cropColor: Int,
    year: Int,
    month: Int,
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel> = emptyList(),
    diaries: List<DiaryModel> = emptyList()
) = remember(cropColor, year, month, selectedDate, onDateSelect, holidays, diaries) {
    DiaryCalendarStateHolder(
        cropColor, year, month, selectedDate, onDateSelect, holidays, diaries
    )
}


@Composable
private fun StatelessDiaryCalendar(
    stateHolder: DiaryCalendarStateHolder,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CalendarDayOfWeekRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Paddings.xlarge)
        )

        for (weekNum in stateHolder.startWeekNum..stateHolder.endWeekNum) {
            val weekDateRange = LocalDateUtil.getWeekDateRange(stateHolder.year, weekNum)
            DiaryCalendarRow(
                modifier = Modifier.padding(bottom = Paddings.medium),
                cropColor = stateHolder.cropColor,
                calendarMonth = stateHolder.month,
                weekDateRange = weekDateRange,
                selectedDate = stateHolder.selectedDate,
                onDateSelect = stateHolder.onDateSelect,
                holidays = stateHolder.holidays.filter { it.date in weekDateRange },
                diaries = stateHolder.diaries.filter { it.date in weekDateRange }
            )
        }
    }
}


@Composable
private fun DiaryCalendarRow(
    @ColorInt cropColor: Int,
    calendarMonth: Int,
    weekDateRange: ClosedRange<LocalDate>,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (date in weekDateRange) {
            DiaryCalendarDateItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onDateSelect(date) }
                    .alpha(
                        if (date.monthValue == calendarMonth) 1f
                        else 0.3f
                    ),
                cropColor = cropColor,
                date = date,
                isSelected = (date == selectedDate),
                isHoliday = holidays.any { it.date == date && it.isHoliday } or (date.dayOfWeek == DayOfWeek.SUNDAY),
                diary = diaries.firstOrNull { it.date == date }
            )
        }
    }
}

@Composable
private fun DiaryCalendarDateItem(
    @ColorInt cropColor: Int,
    date: LocalDate,
    isSelected: Boolean,
    isHoliday: Boolean,
    diary: DiaryModel?,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isSelected) Color.Black
        else if (diary != null) Color(cropColor)
        else Color.Transparent

    val textColor =
        if (isHoliday) MaterialTheme.colors.red1
        else if (isSelected or (diary != null)) Color.White
        else MaterialTheme.colors.text1

    Box(
        modifier = modifier
            .size(CalendarDesignToken.CALENDAR_ITEM_SIZE.dp)
            .aspectRatio(1f)
            .clip(shape = CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(Paddings.medium),
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typo.bodyR,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiaryCalendarPreview() {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    StatelessDiaryCalendar(
        stateHolder = DiaryCalendarStateHolder(
            cropColor = CropModelColorType.POTATO.color,
            year = 2024,
            month = 6,
            selectedDate = selectedDate,
            onDateSelect = { selectedDate = it },
        )
    )
}