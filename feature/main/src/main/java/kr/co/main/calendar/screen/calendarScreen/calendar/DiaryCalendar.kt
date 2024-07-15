package kr.co.main.calendar.screen.calendarScreen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kr.co.common.util.LocalDateUtil
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.DiaryStreakMapper
import kr.co.main.model.calendar.DiaryStreakModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period

private class DiaryCalendarStateHolder(
    val year: Int,
    val month: Int,
    val selectedDate: LocalDate = LocalDate.now(),
    val onDateSelect: (LocalDate) -> Unit,
    val holidays: List<HolidayModel> = emptyList(),
    val diaryStreaks: List<DiaryStreakModel> = emptyList()
) {
    val startWeekNum = LocalDateUtil.getStartWeekNumber(year, month)
    val endWeekNum = LocalDateUtil.getEndWeekNumber(year, month)
}

@Composable
internal fun DiaryCalendar(
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    diaries: List<DiaryModel>,
    modifier: Modifier = Modifier
) {
    val stateHolder = rememberDiaryCalendarStateHolder(
        year = calendarYear,
        month = calendarMonth,
        selectedDate = selectedDate,
        onDateSelect = onDateSelect,
        holidays = holidays,
        diaryStreaks = DiaryStreakMapper.convert(diaries)
    )

    StatelessDiaryCalendar(
        modifier = modifier,
        stateHolder = stateHolder,
    )
}

@Composable
private fun rememberDiaryCalendarStateHolder(
    year: Int,
    month: Int,
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel> = emptyList(),
    diaryStreaks: List<DiaryStreakModel> = emptyList()
) = remember(year, month, selectedDate, onDateSelect, holidays, diaryStreaks) {
    DiaryCalendarStateHolder(
        year, month, selectedDate, onDateSelect, holidays, diaryStreaks
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
                calendarMonth = stateHolder.month,
                weekDateRange = weekDateRange,
                selectedDate = stateHolder.selectedDate,
                onDateSelect = stateHolder.onDateSelect,
                holidays = stateHolder.holidays.filter { it.date in weekDateRange },
                diaryStreaks = stateHolder.diaryStreaks.filter {
                    (it.startDate in weekDateRange) or (it.endDate in weekDateRange)
                }
            )

        }
    }
}


@Composable
private fun DiaryCalendarRow(
    calendarMonth: Int,
    weekDateRange: ClosedRange<LocalDate>,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    diaryStreaks: List<DiaryStreakModel>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        CalendarDateRow(
            modifier = Modifier
                .zIndex(1f),
            calendarMonth = calendarMonth,
            weekDateRange = weekDateRange,
            selectedDate = selectedDate,
            onDateSelect = onDateSelect,
            holidays = holidays
        )
        DiaryItemScope.DiaryCalendarDiaryRow(
            weekDateRange = weekDateRange,
            diaryStreaks = diaryStreaks
        )
    }
}

@Composable
private fun DiaryItemScope.DiaryCalendarDiaryRow(
    weekDateRange: ClosedRange<LocalDate>,
    diaryStreaks: List<DiaryStreakModel>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val content: @Composable () -> Unit = {
        diaryStreaks.forEach { diaryStreak ->
            DiaryCalendarDiaryItem(
                diaryStreak = diaryStreak,
                weekStartDate = weekDateRange.start,
                weekEndDate = weekDateRange.endInclusive
            )
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val singleWidth = constraints.maxWidth / 7

            val placeables = measurables.map {
                val duration = with(it.parentData as DiaryItemParentData) {
                    Period.between(startDate, endDate).days + 1
                }
                it.measure(
                    constraints.copy(maxWidth = singleWidth * duration)
                )
            }

            layout(
                width = constraints.maxWidth,
                height = with(density) {
                    CalendarDesignToken.CALENDAR_ITEM_SIZE.dp.toPx().toInt()
                }
            ) {
                placeables.forEach {
                    it.place(
                        x = with(it.parentData as DiaryItemParentData) {
                            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                            when (startDate.dayOfWeek) {
                                DayOfWeek.SUNDAY -> 0
                                DayOfWeek.MONDAY -> singleWidth * 1
                                DayOfWeek.TUESDAY -> singleWidth * 2
                                DayOfWeek.WEDNESDAY -> singleWidth * 3
                                DayOfWeek.THURSDAY -> singleWidth * 4
                                DayOfWeek.FRIDAY -> singleWidth * 5
                                DayOfWeek.SATURDAY -> singleWidth * 6
                            }
                        },
                        y = 0
                    )
                }
            }
        }
    )
}

@Composable
private fun DiaryItemScope.DiaryCalendarDiaryItem(
    diaryStreak: DiaryStreakModel,
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(CalendarDesignToken.CALENDAR_ITEM_SIZE.dp)
            .fillMaxWidth()
            .diaryDateInfo(
                startDate = if (diaryStreak.startDate <= weekStartDate) weekStartDate else diaryStreak.startDate,
                endDate = if (weekEndDate <= diaryStreak.endDate) weekEndDate else diaryStreak.endDate
            )
            .clip(shape = CircleShape)
            .background(MaterialTheme.colors.primary)
    )
}

@LayoutScopeMarker
@Immutable
object DiaryItemScope {
    @Stable
    fun Modifier.diaryDateInfo(
        startDate: LocalDate,
        endDate: LocalDate
    ) = then(
        DiaryItemParentData(startDate, endDate)
    )
}

private class DiaryItemParentData(
    val startDate: LocalDate,
    val endDate: LocalDate
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@DiaryItemParentData
}

@Preview(showBackground = true)
@Composable
private fun DiaryCalendarPreview() {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    StatelessDiaryCalendar(
        stateHolder = DiaryCalendarStateHolder(
            year = 2024,
            month = 6,
            selectedDate = selectedDate,
            onDateSelect = { selectedDate = it },
        )
    )
}
