package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.HolidayEntity

interface HolidayRepository {
   fun getHolidays(year: Int, month: Int): Flow<List<HolidayEntity>>
}