package kr.co.data.source.remote

import kotlinx.coroutines.flow.Flow
import kr.co.data.model.data.calendar.ScheduleData

interface ScheduleRemoteDataSource {
    suspend fun fetchList(
        crop:String,
        weekStartDate:String
    ): Flow<List<ScheduleData>>

    suspend fun fetchList(
        crop:String,
        year:Int,
        month:Int
    ):  Flow<List<ScheduleData>>

    suspend fun fetchDetail(id:Int):ScheduleData

    suspend fun create(
        category:String,
        title:String,
        startDate:String,
        endDate: String,
        memo:String,
        isAlarmOn:Boolean,
        alarmDateTime:String
    )

    suspend fun update(
        id:Int,
        category:String,
        title:String,
        startDate:String,
        endDate: String,
        memo:String,
        isAlarmOn:Boolean,
        alarmDateTime:String
    )

    suspend fun delete(id:Int)
}