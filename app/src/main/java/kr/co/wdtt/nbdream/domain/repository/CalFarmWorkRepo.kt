package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.source.remote.ApiResponse
import kr.co.wdtt.nbdream.data.source.remote.NsrWorkScheduleResponse
import kr.co.wdtt.nbdream.domain.entity.CalendarFarmWorkEntity

interface CalFarmWorkRepo {
    suspend fun getCalendarFarmWorks(cropCode:String): Flow<List<CalendarFarmWorkEntity>>
    suspend fun getWorkScheduleGroupList(): Flow<ApiResponse<NsrWorkScheduleResponse>>
}