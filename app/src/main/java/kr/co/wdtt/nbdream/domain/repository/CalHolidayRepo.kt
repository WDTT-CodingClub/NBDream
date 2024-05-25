package kr.co.wdtt.nbdream.domain.repository

import kr.co.wdtt.nbdream.domain.entity.CalendarHolidayEntity

interface CalHolidayRepo {
    suspend fun getCalendarHolidays(year: Int, month:Int): List<CalendarHolidayEntity>
}