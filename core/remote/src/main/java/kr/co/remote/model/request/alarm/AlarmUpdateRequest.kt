package kr.co.remote.model.request.alarm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AlarmUpdateRequest(
    @SerialName("commentAlarm")
    val commentAlarm: Boolean,
    @SerialName("scheduleAlarm")
    val scheduleAlarm: Boolean
)