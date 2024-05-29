package kr.co.wdtt.nbdream.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kr.co.wdtt.nbdream.domain.entity.CalendarDateEntity
import kr.co.wdtt.nbdream.domain.entity.DreamCrop
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class GetCalendarDateImpl @Inject constructor(
    private val getHoliday: GetHoliday
    // private val getSchedule: GetHoliday
    // private val getDiary: GetHoliday
): GetCalendarDate {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend operator fun invoke(crop: DreamCrop, year: Int, month: Int): Flow<List<CalendarDateEntity>> {
        if (year !in 2020..2024 || month !in 1..12) return flowOf(emptyList())

        val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
        val calendarDates = mutableListOf<CalendarDateEntity>().apply {
            var weekNo = 1
            for (day in 1..daysInMonth) {
                val dayOfWeek = LocalDate.of(year, month, day).dayOfWeek
                if ((dayOfWeek == DayOfWeek.SUNDAY) && (day != 1)) weekNo++

                add(CalendarDateEntity(year, month, day, dayOfWeek, weekNo))
            }
        }

        // TODO combine schedule & diary
        return getHoliday(year, month).transform { holidays ->
            holidays.forEach { holiday ->
                calendarDates.find { calendarDate ->
                    holiday.date.equals(calendarDate.year, calendarDate.month, calendarDate.day)
                }?.let {
                    it.holidayList += listOf(holiday)
                }
            }
            emit(calendarDates)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun LocalDate.equals(year: Int, month: Int, day: Int): Boolean =
        this.year == year && this.monthValue == month && this.dayOfMonth == day
}