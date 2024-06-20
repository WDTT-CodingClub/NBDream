package kr.co.main.calendar.ui.common.inner_calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

internal class InnerCalendarStateHolder(
    private val year: Int,
    private val month: Int
) {
    private val calendar = Calendar.getInstance(Locale.getDefault())

    val startWeekNumber: Int = calendar.apply {
        val (newYear, newMonth) = monthBefore(year, month)
        set(newYear, newMonth, 1)
    }.get(Calendar.WEEK_OF_YEAR)

    val endWeekNumber: Int = calendar.apply {
        set(year, month, 1)
    }.get(Calendar.WEEK_OF_YEAR)

    private fun monthBefore(year: Int, month: Int) =
        if (month - 1 < 1) Pair(year - 1, 12) else Pair(year, month - 1)

    fun getWeekRange(year: Int, weekNumber: Int): Pair<LocalDate, LocalDate> {
        val weekFields = WeekFields.of(Locale.getDefault())

        val firstDayOfWeek = LocalDate.of(year, 1, 1)
            .with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY))

        val startOfWeek = firstDayOfWeek
            .with(weekFields.weekOfYear(), weekNumber.toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val endOfWeek = startOfWeek.plusDays(6)

        return Pair(startOfWeek, endOfWeek)
    }
}