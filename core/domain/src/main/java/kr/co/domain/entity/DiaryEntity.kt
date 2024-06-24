package kr.co.domain.entity

import kr.co.domain.entity.type.CropType
import kr.co.domain.entity.type.WorkDescriptionType
import java.time.LocalDate

data class DiaryEntity(
    val id: Int,
    val cropType: CropType,
    val registerDate: LocalDate,
    val holidays: List<HolidayEntity> = emptyList(),
    val weatherForecast: WeatherForecastEntity,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescriptionEntity> = emptyList(),
    val images: List<String> = emptyList(),
    val memo: String = ""
) {
    data class WorkDescriptionEntity(
        val id: Int,
        val type: WorkDescriptionType,
        val description: String,
    )
}




