package kr.co.wdtt.nbdream.domain.repository

import kr.co.wdtt.nbdream.domain.entity.HolidayEntity

interface HolidayRepository {
    suspend fun getCalendarHolidays(year: Int, month:Int): List<HolidayEntity>
}