package kr.co.main.calendar.screen.calendarScreen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kr.co.common.util.LocalDateUtil
import kr.co.common.util.iterator
import kr.co.domain.entity.type.HolidayType
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.ScheduleModel
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period

private class ScheduleCalendarStateHolder(
    val year: Int,
    val month: Int,
    val selectedDate: LocalDate = LocalDate.now(),
    val onDateSelect: (LocalDate) -> Unit,
    val holidays: List<HolidayModel> = emptyList(),
    val allSchedules: List<ScheduleModel> = emptyList(),
    val cropSchedules: List<ScheduleModel> = emptyList()
) {
    val startWeekNum = LocalDateUtil.getStartWeekNumber(year, month)
    val endWeekNum = LocalDateUtil.getEndWeekNumber(year, month)
}


@Composable
internal fun ScheduleCalendar(
    calendarYear: Int,
    calendarMonth: Int,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    allSchedules: List<ScheduleModel>,
    cropSchedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {
    val stateHolder = rememberScheduleCalendarStateHolder(
        calendarMonth,
        calendarYear,
        selectedDate,
        onDateSelect,
        holidays,
        allSchedules,
        cropSchedules
    )
    StatelessScheduleCalendar(
        stateHolder = stateHolder,
        modifier = modifier
    )
}

@Composable
private fun rememberScheduleCalendarStateHolder(
    year: Int,
    month: Int,
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel> = emptyList(),
    allSchedules: List<ScheduleModel> = emptyList(),
    cropSchedules: List<ScheduleModel> = emptyList(),
) = remember {
    ScheduleCalendarStateHolder(
        year, month, selectedDate, onDateSelect,
        holidays, allSchedules, cropSchedules
    )
}


@Composable
private fun StatelessScheduleCalendar(
    stateHolder: ScheduleCalendarStateHolder,
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
            ScheduleCalendarRow(
                modifier = Modifier.padding(bottom = Paddings.medium),
                calendarMonth = stateHolder.month,
                weekDateRange = weekDateRange,
                selectedDate = stateHolder.selectedDate,
                onDateSelect = stateHolder.onDateSelect,
                holidays = stateHolder.holidays.filter { it.date in weekDateRange },
                allSchedules = stateHolder.allSchedules.filter {
                    (it.startDate in weekDateRange) or (it.endDate in weekDateRange)
                },
                cropSchedules = stateHolder.cropSchedules.filter {
                    (it.startDate in weekDateRange) or (it.endDate in weekDateRange)
                }
            )

        }
    }
}


@Composable
private fun ScheduleCalendarRow(
    calendarMonth: Int,
    weekDateRange: ClosedRange<LocalDate>,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    allSchedules: List<ScheduleModel>,
    cropSchedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ScheduleCalendarDateRow(
            modifier = Modifier
                .zIndex(1f),
            calendarMonth = calendarMonth,
            weekDateRange = weekDateRange,
            selectedDate = selectedDate,
            onDateSelect = onDateSelect,
            holidays = holidays
        )
        ScheduleItemScope.ScheduleCalendarScheduleRow(
            weekDateRange = weekDateRange,
            allSchedules = allSchedules,
            cropSchedules = cropSchedules
        )
    }
}

@Composable
private fun ScheduleCalendarDateRow(
    calendarMonth: Int,
    weekDateRange: ClosedRange<LocalDate>,
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
    holidays: List<HolidayModel>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (date in weekDateRange) {
            ScheduleCalendarDateItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onDateSelect(date) }
                    .alpha(
                        if (date.monthValue == calendarMonth) 1f
                        else 0.3f
                    ),
                date = date,
                isSelected = (date == selectedDate),
                isHoliday = holidays.any { it.date == date } or (date.dayOfWeek == DayOfWeek.SUNDAY),
            )
        }
    }
}

@Composable
private fun ScheduleCalendarDateItem(
    date: LocalDate,
    isSelected: Boolean,
    isHoliday: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isSelected) Color.Black
        else Color.Transparent

    val textColor =
        if (isHoliday) MaterialTheme.colors.red1
        else if (isSelected) Color.White
        else MaterialTheme.colors.text1

    Box(
        modifier = modifier
            .size(CalendarDesignToken.CALENDAR_ITEM_SIZE.dp)
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

@Composable
private fun ScheduleItemScope.ScheduleCalendarScheduleRow(
    weekDateRange: ClosedRange<LocalDate>,
    allSchedules: List<ScheduleModel>,
    cropSchedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val content: @Composable () -> Unit = {
        (allSchedules + cropSchedules).forEach { schedule ->
            ScheduleCalendarScheduleItem(
                schedule = schedule,
                weekStartDate = weekDateRange.start,
                weekEndDate = weekDateRange.endInclusive
            )
        }
    }

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                val duration = with(it.parentData as ScheduleItemParentData) {
                    Period.between(startDate, endDate).days + 1
                }
                it.measure(
                    constraints.copy(
                        maxWidth = (constraints.maxWidth / 7) * (duration)
                    )
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
                        x = with(it.parentData as ScheduleItemParentData) {
                            when (startDate.dayOfWeek) {
                                DayOfWeek.SUNDAY -> 0
                                DayOfWeek.MONDAY -> (constraints.maxWidth / 7) * 1
                                DayOfWeek.TUESDAY -> (constraints.maxWidth / 7) * 2
                                DayOfWeek.WEDNESDAY -> (constraints.maxWidth / 7) * 3
                                DayOfWeek.THURSDAY -> (constraints.maxWidth / 7) * 4
                                DayOfWeek.FRIDAY -> (constraints.maxWidth / 7) * 5
                                DayOfWeek.SATURDAY -> (constraints.maxWidth / 7) * 6
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
private fun ScheduleItemScope.ScheduleCalendarScheduleItem(
    schedule: ScheduleModel,
    weekStartDate: LocalDate,
    weekEndDate: LocalDate,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(CalendarDesignToken.CALENDAR_ITEM_SIZE.dp)
            .fillMaxWidth()
            .scheduleDateInfo(
                startDate = if (schedule.startDate <= weekStartDate) weekStartDate else schedule.startDate,
                endDate = if (weekEndDate <= schedule.endDate) weekEndDate else schedule.endDate
            )
            .clip(shape = CircleShape)
            .apply {
                if (schedule.startDate == schedule.endDate) aspectRatio(1f)
            }
            .background(color = Color(schedule.type.color).copy(alpha = 0.25f))
    )
}

@LayoutScopeMarker
@Immutable
object ScheduleItemScope {
    @Stable
    fun Modifier.scheduleDateInfo(
        startDate: LocalDate,
        endDate: LocalDate
    ) = then(
        ScheduleItemParentData(startDate, endDate)
    )
}

private class ScheduleItemParentData(
    val startDate: LocalDate,
    val endDate: LocalDate
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@ScheduleItemParentData
}

@Preview
@Composable
private fun ScheduleCalendarScheduleItemPreview() {
    ScheduleItemScope.ScheduleCalendarScheduleItem(
        schedule = ScheduleModel(
            id = 1,
            type = ScheduleModelType.All,
            title = "",
            startDate = LocalDate.of(2024, 6, 14),
            endDate = LocalDate.of(2024, 6, 18),
        ),
        weekStartDate = LocalDate.of(2024, 6, 16),
        weekEndDate = LocalDate.of(2024, 6, 22)
    )
}


@Preview(showBackground = true)
@Composable
private fun ScheduleCalendarPreview() {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    StatelessScheduleCalendar(
        stateHolder = ScheduleCalendarStateHolder(
            year = 2024,
            month = 6,
            selectedDate = selectedDate,
            onDateSelect = { selectedDate = it },
            holidays = listOf(
                HolidayModel(
                    date = LocalDate.of(2024, 6, 6),
                    name = "",
                    isHoliday = true,
                    type = HolidayType.NATIONAL_HOLIDAY
                ),
                HolidayModel(
                    date = LocalDate.of(2024, 6, 20),
                    name = "",
                    isHoliday = true,
                    type = HolidayType.NATIONAL_HOLIDAY
                ),
                HolidayModel(
                    date = LocalDate.of(2024, 6, 24),
                    name = "",
                    isHoliday = true,
                    type = HolidayType.NATIONAL_HOLIDAY
                )
            ),
            allSchedules = listOf(
                ScheduleModel(
                    id = 1,
                    type = ScheduleModelType.All,
                    title = "",
                    startDate = LocalDate.of(2024, 6, 14),
                    endDate = LocalDate.of(2024, 6, 18),
                ),
                ScheduleModel(
                    id = 1,
                    type = ScheduleModelType.All,
                    title = "",
                    startDate = LocalDate.of(2024, 6, 6),
                    endDate = LocalDate.of(2024, 6, 8),
                ),
                ScheduleModel(
                    id = 1,
                    type = ScheduleModelType.All,
                    title = "",
                    startDate = LocalDate.of(2024, 6, 17),
                    endDate = LocalDate.of(2024, 6, 17),
                )
            ),
            cropSchedules = listOf(
                ScheduleModel(
                    id = 1,
                    type = ScheduleModelType.Crop(CropModel.create(CropModelType.POTATO)),
                    title = "",
                    startDate = LocalDate.of(2024, 6, 4),
                    endDate = LocalDate.of(2024, 6, 8),
                ),
                ScheduleModel(
                    id = 1,
                    type = ScheduleModelType.Crop(CropModel.create(CropModelType.POTATO)),
                    title = "",
                    startDate = LocalDate.of(2024, 6, 30),
                    endDate = LocalDate.of(2024, 7, 2),
                ),
                ScheduleModel(
                    id = 1,
                    type = ScheduleModelType.Crop(CropModel.create(CropModelType.POTATO)),
                    title = "",
                    startDate = LocalDate.of(2024, 6, 1),
                    endDate = LocalDate.of(2024, 6, 1),
                )
            )
        )
    )
}