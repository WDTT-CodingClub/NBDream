package kr.co.domain.entity.type

enum class HolidayType(
    val typeString: String,
    val priority: Int
) {
    NATIONAL_HOLIDAY("01", 1), //국경일, 공휴일 (달력 빨간 날)
    CONSTITUTION_DAY("01", 2), //제헌절
    ANNIVERSARY("02", 3), //기념일
    SOLAR_TERM("03", 4), //24절기
    ETC("04", 5); //잡절

    companion object{
        fun ofValue(typeString: String) = when(typeString){
            NATIONAL_HOLIDAY.typeString -> NATIONAL_HOLIDAY
            CONSTITUTION_DAY.typeString -> CONSTITUTION_DAY
            ANNIVERSARY.typeString -> ANNIVERSARY
            SOLAR_TERM.typeString -> SOLAR_TERM
            ETC.typeString -> ETC
            else -> throw IllegalArgumentException("Unknown holiday type")
        }
    }
}