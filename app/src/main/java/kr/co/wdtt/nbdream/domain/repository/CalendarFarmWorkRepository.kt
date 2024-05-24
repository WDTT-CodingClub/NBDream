package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.CalendarFarmWorkEntity

interface CalendarFarmWorkRepository {
    suspend fun getCalendarFarmWorks(cropCode:String): Flow<List<CalendarFarmWorkEntity>>
}