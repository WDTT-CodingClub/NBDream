package kr.co.remote.model.response.calendar

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleListResponse (
    val scheduleList: List<ScheduleResponse>
){
    @Serializable
    data class ScheduleResponse(
        val id: Long,
        val category:String,
        val title:String,
        val startDate:String,
        val endDate: String,
        val memo:String
    )
}