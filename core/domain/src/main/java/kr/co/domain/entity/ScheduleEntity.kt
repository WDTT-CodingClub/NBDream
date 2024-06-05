package kr.co.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleEntity(
    val id: String? = null,
    val category: ScheduleCategory,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo:String,
    val isAlarmOn: Boolean = false,
    val alarmDateTime: LocalDateTime? = null
)

sealed class ScheduleCategory{
    data object All : ScheduleCategory()
    data class Crop(val dreamCrop: DreamCrop): ScheduleCategory()
}

