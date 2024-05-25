package kr.co.wdtt.nbdream.domain.entity

data class CalendarScheduleEntity(
    val id: String? = null,
    val userId: String? = null,
    val scheduleType: ScheduleType,
    val title: String,
    val startDate: String,
//    val startYear: Int,
//    val startMonth: Int,
//    val startDay: Int,
    val endDate: String,
//    val endYear:Int,
//    val endMonth: Int,
//    val endDay: Int,
    val memo:String,
    val isAlarmOn: Boolean = false,
    val alarmDateTime: String? = null
)

sealed class ScheduleType {
    data object ALL_CROP:ScheduleType()
    data class CROP(val cropCode: String): ScheduleType()
}

