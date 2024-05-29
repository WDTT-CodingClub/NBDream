package kr.co.wdtt.nbdream.domain.entity

import java.time.LocalDate

data class HolidayEntity (
    val date: LocalDate,
    val isHoliday: Boolean,
    val type:HolidayType,
    val name: String
)

enum class HolidayType{
    NATIONAL_HOLIDAY, //국경일, 공휴일 (달력 빨간 날)
    CONSTITUTION_DAY, //제헌절
    ANNIVERSARY, //기념일
    SOLAR_TERM, //24절기
    ETC //잡절
}