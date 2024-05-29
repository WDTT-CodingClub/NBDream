package kr.co.wdtt.nbdream.domain.entity

import java.time.LocalDate

data class ScheduleEntity(
    val id: String? = null,
    val userId: String? = null,
    val dreamCrop: DreamCrop?= null,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val memo:String,
    val isAlarmOn: Boolean = false,
    val alarmDateTime: String? = null
)

sealed class ScheduleType{
    data object ALL_CROP : ScheduleType()
    data class CROP(val dreamCrop: DreamCrop): ScheduleType()
}

