package kr.co.common.util

import android.annotation.SuppressLint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

object LocalDateUtil {
    @SuppressLint("ConstantLocale")
    private val calendar = Calendar.getInstance(Locale.getDefault())

    // year년 month달 weekNum 범위
    fun getMonthWeekRange(year: Int, month:Int): ClosedRange<Int> =
        (getStartWeekNumber(year, month)..getEndWeekNumber(year, month))

    // year년 weekNum번째 주 날짜 범위 (일요일부터 시작)
    fun getWeekDateRange(year: Int, weekNumber: Int): ClosedRange<LocalDate> {
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

    // year년 month달의 첫 주
    private fun getStartWeekNumber(year: Int, month: Int): Int =
        calendar.apply {
            val (newYear, newMonth) = monthBefore(year, month)
            set(newYear, newMonth, 1)
        }.get(Calendar.WEEK_OF_YEAR)

    // year년 month달의 마지막 주
    private fun getEndWeekNumber(year: Int, month: Int): Int =
        calendar.apply {
            set(year, month, 1)
        }.get(Calendar.WEEK_OF_YEAR)
}

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> {
    return object : Iterator<LocalDate> {
        private var next = this@iterator.start
        private val finalElement = this@iterator.endInclusive
        private var hasNext = !next.isAfter(this@iterator.endInclusive)

        override fun hasNext(): Boolean = hasNext
        override fun next(): LocalDate {
            val value = next
            if (value == finalElement) {
                hasNext = false
            } else {
                next = next.plusDays(1)
            }
            return value
        }
    }
}
