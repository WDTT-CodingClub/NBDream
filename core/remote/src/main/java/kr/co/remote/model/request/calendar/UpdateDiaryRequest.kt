package kr.co.remote.model.request.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateDiaryRequest (
    val crop:String,
    val date: String,
    @SerialName("holidayResponses")
    val holidayList: List<PostDiaryRequest.HolidayRequest>,
    val weatherForecast: String,
    @SerialName("workingPeopleNumber")
    val workLaborer: Int,
    @SerialName("workingTime")
    val workHours: Int,
    @SerialName("workingArea")
    val workArea: Int,
    @SerialName("workDtos")
    val workDescriptions: List<PostDiaryRequest.WorkDescriptionRequest>,
    val imageUrls: List<String>,
    val memo: String
)