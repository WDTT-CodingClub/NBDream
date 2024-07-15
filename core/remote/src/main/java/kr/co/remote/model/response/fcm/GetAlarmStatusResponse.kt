package kr.co.remote.model.response.fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetAlarmStatusResponse(
    @SerialName("commentAlarm")
    val commentAlarm: Boolean,
    @SerialName("scheduleAlarm")
    val scheduleAlarm: Boolean
)