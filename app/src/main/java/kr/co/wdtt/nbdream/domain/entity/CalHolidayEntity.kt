package kr.co.wdtt.nbdream.domain.entity

data class CalHolidayEntity (
    val year:Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: DayOfWeek,
    val holidays: List<Holiday> = emptyList()
)

data class Holiday(
    val isHoliday: Boolean,
    val type:HolidayType,
    val name: String
)

enum class DayOfWeek{
    SUN,
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT
}

enum class HolidayType{
    NATIONAL_HOLIDAY,
    CONSTITUTION_DAY,
    SOLAR_TERM,
    ETC
}