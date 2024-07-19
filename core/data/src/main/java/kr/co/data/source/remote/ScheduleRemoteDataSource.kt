package kr.co.data.source.remote

import kotlinx.coroutines.flow.Flow
import kr.co.data.model.data.calendar.ScheduleData

interface ScheduleRemoteDataSource {
    suspend fun fetchList(): Flow<List<ScheduleData>>

    suspend fun fetchList(
        category:String,
        year:Int,
        month:Int
    ):  Flow<List<ScheduleData>>

    suspend fun fetchDetail(id:Long):ScheduleData

    suspend fun create(
        category:String,
        title:String,
        startDate:String,
        endDate: String,
        memo:String,
        alarmOn:Boolean,
        alarmDateTime:String
    )

    suspend fun update(
        id:Long,
        category:String,
        title:String,
        startDate:String,
        endDate: String,
        memo:String,
        alarmOn:Boolean,
        alarmDateTime:String
    )

    suspend fun delete(id:Long)
}