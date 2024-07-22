package kr.co.data.model.data.calendar

data class ScheduleData (
    val id: Long,
    val category:String,
    val title:String,
    val startDate:String,
    val endDate: String,
    val memo:String,
    val alarmOn:Boolean,
    val alarmDateTime:String
)