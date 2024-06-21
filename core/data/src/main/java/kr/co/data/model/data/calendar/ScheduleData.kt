package kr.co.data.model.data.calendar

data class ScheduleData (
    val id: Int,
    val category:String,
    val title:String,
    val startDate:String,
    val endDate: String,
    val memo:String,
    val isAlarmOn:Boolean,
    val alarmDateTime:String
)