package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.ScheduleEntity

interface ScheduleRepository {
    suspend fun getSchedules():
            Flow<List<ScheduleEntity>>

    suspend fun getSchedules(category: String, year: Int, month: Int):
            Flow<List<ScheduleEntity>>

    suspend fun getScheduleDetail(id:Long):ScheduleEntity

    suspend fun createSchedule(
        category: String,
        title: String,
        startDate: String,
        endDate: String,
        memo: String
    )

    suspend fun updateSchedule(
        id: Long,
        category: String,
        title: String,
        startDate: String,
        endDate: String,
        memo: String
    )

    suspend fun deleteSchedule(id: Long)
}