package kr.co.domain.entity

import java.time.LocalDate

data class HolidayEntity (
    val date: LocalDate,
    val isHoliday: Boolean,
    val type: Type,
    val name: String
) {
    enum class Type(val priority: Int) {
        NATIONAL_HOLIDAY(1), //국경일, 공휴일 (달력 빨간 날)
        CONSTITUTION_DAY(2), //제헌절
        ANNIVERSARY(3), //기념일
        SOLAR_TERM(4), //24절기
        ETC(5) //잡절
    }
}