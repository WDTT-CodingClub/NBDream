package kr.co.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleEntity(
    val id: String? = null,
    val category: Category,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo:String = "",
    val isAlarmOn: Boolean = false,
    val alarmDateTime: LocalDateTime? = null
){
    sealed class Category{
        data object All : Category()
        data class Crop(val crop: CropEntity): Category()
    }
}



