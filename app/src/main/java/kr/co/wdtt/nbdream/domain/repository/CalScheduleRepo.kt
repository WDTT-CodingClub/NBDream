package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.CalScheduleEntity

interface CalScheduleRepo {
    //TODO 서버에 작물 정보 어떤 형태로 보내야 할지 상의
    suspend fun getMonthCalendarSchedules(userId:String, cropCode: String, year:Int, month:Int):
            Flow<List<CalScheduleEntity>>
    suspend fun getWeekCalendarSchedules(userId:String, cropCode: String, year:Int, month:Int, date:Int):
            Flow<List<CalScheduleEntity>>

    // TODO 추가/편집 요청 따로 해야 하는지 상의
    suspend fun upsertCalendarSchedule(calScheduleEntity: CalScheduleEntity)
    suspend fun deleteCalendarSchedule(calendarScheduleId: String)

    suspend fun searchCalendarSchedule(
        userId:String,
        keyword:String,
        startYear:Int,
        startMonth:Int,
        startDay:Int,
        endYear:Int,
        endMonth:Int,
        endDay:Int,
        sort:String = "recent"
    ): Flow<List<CalScheduleEntity>>
}