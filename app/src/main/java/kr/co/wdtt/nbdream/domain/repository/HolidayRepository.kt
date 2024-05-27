package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity

interface HolidayRepository {
    suspend fun getHolidays(year: Int, month:Int): Flow<List<EntityWrapper<List<HolidayEntity>>>>
}