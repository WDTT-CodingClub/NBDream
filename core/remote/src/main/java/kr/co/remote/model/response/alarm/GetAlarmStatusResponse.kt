package kr.co.remote.model.response.alarm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetAlarmStatusResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        @SerialName("commentAlarm")
        val commentAlarm: Boolean,
        @SerialName("scheduleAlarm")
        val scheduleAlarm: Boolean
    )
}