package kr.co.main.calendar.common.innerCalendar

import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.ScheduleModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

internal sealed class InnerCalendarItem {
    abstract val startDate: LocalDate
    abstract val endDate: LocalDate

    data class ScheduleItem(
        override val startDate: LocalDate,
        override val endDate: LocalDate,
        val schedule: ScheduleModel
    ) : InnerCalendarItem()

    data class DiaryItem(
        override val startDate: LocalDate,
        override val endDate: LocalDate,
        val diaries: List<DiaryModel>
    ) : InnerCalendarItem()
}

internal fun filterAndSortItems(
    items: List<InnerCalendarItem>,
    dateRange: ClosedRange<LocalDate>
) = items
    .filter { (it.startDate in dateRange) or (it.endDate in dateRange) }
    .sortedWith(compareBy({ it.startDate }, { it.endDate }))


internal class InnerCalendarStateHolder(
    val year: Int,
    val month: Int,
    val selectedDate: LocalDate = LocalDate.now(),
    val holidays: List<HolidayModel> = emptyList(),
    val items: List<InnerCalendarItem> = emptyList()
) {
    private val calendar = Calendar.getInstance(Locale.getDefault())

    // year년 month달의 첫 주 = year년 startWeekNumber번째 주
    private val startWeekNumber: Int = calendar.apply {
        val (newYear, newMonth) = monthBefore(year, month)
        set(newYear, newMonth, 1)
    }.get(Calendar.WEEK_OF_YEAR)

    // year년 month달의 마지막 주 = year년 endWeekNumber번째 주
    private val endWeekNumber: Int = calendar.apply {
        set(year, month, 1)
    }.get(Calendar.WEEK_OF_YEAR)

    // year년 month달 범위 = startWeekNumber번째 주 ~ endWeekNumber번째 주
    fun getMonthWeekRange(): ClosedRange<Int> = (startWeekNumber..endWeekNumber)

    // year년 weekNumber번째 주 날짜 범위 (일요일부터 시작) = startWeekDate ~ endWeekDate
    fun getWeekDateRange(weekNumber: Int): ClosedRange<LocalDate> {
        val weekFields = WeekFields.of(Locale.getDefault())

        val firstDayOfWeek = LocalDate.of(year, 1, 1)
            .with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY))

        val startOfWeek = firstDayOfWeek
            .with(weekFields.weekOfYear(), weekNumber.toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val endOfWeek = startOfWeek.plusDays(6)

        return (startOfWeek..endOfWeek)
    }

    private fun monthBefore(year: Int, month: Int) =
        if (month - 1 < 1) Pair(year - 1, 12) else Pair(year, month - 1)
}
