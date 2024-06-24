package kr.co.main.calendar.model

import kr.co.main.calendar.model.type.ScheduleModelType
import java.time.LocalDate

internal data class ScheduleModel(
    val id: Int,
    val type: ScheduleModelType,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo: String = ""
)