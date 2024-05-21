package kr.co.wdtt.nbdream.domain.entity

data class CalendarScheduleEntity(
    val title: String,
    val crops: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val aram: Boolean,
    val aramTime: String?,
)
