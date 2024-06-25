package kr.co.domain.entity

import kr.co.domain.entity.type.ScheduleType
import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleEntity(
    val id: Long,
    val type: ScheduleType,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo: String = ""
)



