package kr.co.main.calendar.model

import kr.co.domain.entity.ScheduleEntity
import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleModel(
    val id: String? = null,
    val category: Category,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate = startDate,
    val memo: String = "",
    val isAlarmOn: Boolean = false,
    val alarmDateTime: LocalDateTime? = null
) {
    sealed class Category {
        data object All : Category()
        data class Crop(val crop: CropModel) : Category()
    }
}

internal fun ScheduleEntity.convert() = ScheduleModel(
    id = id,
    category = when (category) {
        is ScheduleEntity.Category.All -> ScheduleModel.Category.All
        is ScheduleEntity.Category.Crop -> {
            ScheduleModel.Category.Crop((category as ScheduleEntity.Category.Crop).crop.convert())
        }
    },
    title = title,
    startDate = startDate,
    endDate = endDate,
)

internal fun ScheduleModel.convert() = ScheduleEntity(
    id = id,
    category = when (category) {
        is ScheduleModel.Category.All -> ScheduleEntity.Category.All
        is ScheduleModel.Category.Crop -> {
            ScheduleEntity.Category.Crop(category.crop.convert())
        }
    },
    title = title,
    startDate = startDate,
    endDate = endDate,
)