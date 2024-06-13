package kr.co.main.calendar.calendar.maincalendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
internal class MainCalendarStateHolder(
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

        // 주의 첫 번째 날 (일요일 혹은 월요일 등)
        val firstDayOfWeek = LocalDate.of(year, 1, 1)
            .with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY))

        // 주의 시작 날짜를 계산
        val startOfWeek = firstDayOfWeek
            .with(weekFields.weekOfYear(), weekNumber.toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

        // 주의 끝 날짜를 계산
        val endOfWeek = startOfWeek.plusDays(6)

        return Pair(startOfWeek, endOfWeek)
    }
}