package kr.co.wdtt.nbdream.domain.repository

import kr.co.wdtt.nbdream.domain.entity.CalendarHolidayEntity

interface CalendarHolidayRepository {
    suspend fun getCalendarHolidays(year: Int, month:Int): List<CalendarHolidayEntity>
}