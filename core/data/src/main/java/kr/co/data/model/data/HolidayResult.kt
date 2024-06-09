package kr.co.data.model.data

data class HolidayResult(
    val date: Int,
    val isHoliday: Boolean,
    val type: String,
    val name: String
)