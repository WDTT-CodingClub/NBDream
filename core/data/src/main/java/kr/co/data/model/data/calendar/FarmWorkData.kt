package kr.co.data.model.data.calendar

data class FarmWorkData(
    val id: Int,
    val startMonth: Int,
    val startEra: String,
    val endMonth: Int,
    val endEra: String,
    val farmWorkCategory:String,
    val farmWork: String,
    val videoUrl:String
)