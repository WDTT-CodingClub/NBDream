package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity

interface HolidayRepository {
    suspend fun getHoliday(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>>
    suspend fun getAnniversary(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>>
    suspend fun getSolarTerm(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>>
    suspend fun getEtc(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>>
}