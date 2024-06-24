package kr.co.main.calendar.model

import kr.co.main.calendar.model.type.WorkDescriptionModelType
import java.time.LocalDate

internal data class DiaryModel(
    val id: Int,
    val date: LocalDate,
    val holidays: List<HolidayModel> = emptyList(),
    val weatherInfo: String,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescriptionModel> = emptyList(),
    val images: List<String> = emptyList(),
    val memo: String = ""
) {
    data class WorkDescriptionModel(
        val id: Int,
        val type: WorkDescriptionModelType,
        val description: String
    )
}

