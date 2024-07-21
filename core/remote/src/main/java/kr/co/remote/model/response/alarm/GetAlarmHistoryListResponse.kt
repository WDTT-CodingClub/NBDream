package kr.co.remote.model.response.alarm

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAlarmHistoryListResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val alarmHistoryList: List<AlarmHistory>
    ) {
        @Serializable
        data class AlarmHistory(
            val id: Long,
            val alarmType: String,
            val title: String,
            val content: String,
            val checked: Boolean
        )
    }
}
