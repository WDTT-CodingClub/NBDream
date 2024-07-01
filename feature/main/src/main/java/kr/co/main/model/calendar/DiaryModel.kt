package kr.co.main.model.calendar

import kr.co.main.model.calendar.type.WorkDescriptionModelType
import java.time.LocalDate

internal data class DiaryModel(
    val id: Long,
    val date: LocalDate,
    val images: List<String> = emptyList(),
    val memo: String = "",
    val workDescriptions: List<WorkDescriptionModel> = emptyList(),
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val holidays: List<HolidayModel> = emptyList(),
    val weatherInfo: String
) {
    data class WorkDescriptionModel(
        val type: WorkDescriptionModelType,
        val description: String
    )
}

