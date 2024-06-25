package kr.co.domain.entity

import kr.co.domain.entity.type.WorkDescriptionType
import java.time.LocalDate

data class DiaryEntity(
    val id: Int,
    val date: LocalDate,
    val holidays: List<HolidayEntity> = emptyList(),
    val weatherInfo: String,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescriptionEntity> = emptyList(),
    val images: List<String> = emptyList(),
    val memo: String = ""
) {
    data class WorkDescriptionEntity(
        val type: WorkDescriptionType,
        val description: String,
    )
}




