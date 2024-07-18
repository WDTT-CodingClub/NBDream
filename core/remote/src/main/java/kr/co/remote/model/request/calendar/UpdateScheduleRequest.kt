package kr.co.remote.model.request.calendar

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateScheduleRequest(
    val id: Long,
    val category: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val memo: String,
    val alarmOn: Boolean,
    val alarmDateTime: String
)