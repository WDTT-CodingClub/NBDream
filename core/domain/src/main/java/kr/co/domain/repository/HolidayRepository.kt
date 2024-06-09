package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.HolidayEntity

interface HolidayRepository {
   suspend fun get(year: Int, month: Int): Flow<List<HolidayEntity>>
}