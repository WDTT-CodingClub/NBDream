package kr.co.remote.model.request.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kr.co.remote.serializer.LocalDateSerializer
import java.time.LocalDate

@Serializable
internal data class PostDiaryRequest (
    val crop:String,

    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,

    @SerialName("holidayResponses")
    val holidayList: List<HolidayRequest>,

    val weatherForecast: String,

    val imageUrls: List<String>,

    @SerialName("workingPeopleNumber")
    val workLaborer: Int,

    @SerialName("workingTime")
    val workHours: Int,

    @SerialName("workingArea")
    val workArea: Int,

    @SerialName("workDtos")
    val workDescriptions: List<WorkDescriptionRequest>,
    val memo: String
){
    @Serializable
    data class HolidayRequest(
        @SerialName("dateKind")
        val type: String,

        @SerialName("localDate")
        val date: String,

        val isHoliday: String,

        @SerialName("dateName")
        val name: String
    )

    @Serializable
    data class WorkDescriptionRequest(
        @SerialName("workCategory")
        val type: String,

        @SerialName("content")
        val description: String
    )
}