package kr.co.main.model.calendar

import kr.co.main.model.calendar.type.ScheduleModelType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal data class ScheduleModel(
    val id: Long,
    val type: ScheduleModelType,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val memo: String = "",
    val isAlarmOn: Boolean = false,
    val alarmDateTime: LocalDateTime = LocalDateTime.of(startDate, LocalTime.of(7, 0))
)