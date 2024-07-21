package kr.co.domain.entity

data class AlarmHistoryEntity(
    val id: Long,
    val alarmType: String,
    val title: String,
    val content: String,
    val checked: Boolean
)