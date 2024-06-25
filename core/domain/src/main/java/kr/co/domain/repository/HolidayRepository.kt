package kr.co.domain.repository

import kr.co.domain.entity.HolidayEntity

interface HolidayRepository {
    suspend fun getHolidays(year: Int, month: Int): List<HolidayEntity>
}