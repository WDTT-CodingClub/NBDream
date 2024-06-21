package kr.co.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleEntity(
    val id: Int,
    val category: Category,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo:String = "",
    val isAlarmOn: Boolean = false,
    val alarmDateTime: LocalDateTime? = null
){
    sealed class Category(val koreanName:String){
        data object All : Category("전체")
        data class Crop(val crop: CropEntity): Category(crop.name.koreanName)
    }
}



