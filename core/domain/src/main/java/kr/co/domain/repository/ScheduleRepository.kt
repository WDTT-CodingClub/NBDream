package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.ScheduleEntity

interface ScheduleRepository {
    suspend fun getSchedules(crop: String, startDate: String):
            Flow<List<ScheduleEntity>>

    suspend fun getSchedules(crop: String, year: Int, month: Int):
            Flow<List<ScheduleEntity>>

    suspend fun getScheduleDetail(id:Int):ScheduleEntity

    suspend fun createSchedule(
        category: String,
        title: String,
        startDate: String,
        endDate: String,
        memo: String,
        isAlarmOn: Boolean,
        alarmDateTime: String
    )

    suspend fun updateSchedule(
        id: Int,
        category: String,
        title: String,
        startDate: String,
        endDate: String,
        memo: String,
        isAlarmOn: Boolean,
        alarmDateTime: String

    )

    suspend fun deleteSchedule(id: Int)
}