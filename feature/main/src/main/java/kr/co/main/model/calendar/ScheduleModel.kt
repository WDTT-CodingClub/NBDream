package kr.co.main.model.calendar

import kr.co.main.model.calendar.type.ScheduleModelType
import java.time.LocalDate

internal data class ScheduleModel(
    val id: Int,
    val type: ScheduleModelType,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo: String = ""
)