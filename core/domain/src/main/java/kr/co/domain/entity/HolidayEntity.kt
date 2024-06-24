package kr.co.domain.entity

import kr.co.domain.entity.type.HolidayType
import java.time.LocalDate

data class HolidayEntity (
    val date: LocalDate,
    val name: String,
    val type: HolidayType,
    val isHoliday: Boolean
)