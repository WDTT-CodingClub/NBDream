package kr.co.wdtt.nbdream.domain.repository

import kr.co.wdtt.nbdream.domain.entity.CalHolidayEntity

interface CalHolidayRepo {
    suspend fun getCalendarHolidays(year: Int, month:Int): List<CalHolidayEntity>
}