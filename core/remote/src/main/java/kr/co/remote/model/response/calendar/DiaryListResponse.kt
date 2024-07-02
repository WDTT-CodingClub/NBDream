package kr.co.remote.model.response.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class DiaryListResponse(
    @SerialName("response")
    val diaryList: List<DiaryResponse>,
) {

    @Serializable
    data class DiaryResponse(

        @SerialName("farmingDiaryId")
        val id: Long,
        val date: String,

        @SerialName("holidayResponses")
        val holidayList: List<HolidayResponse>,

        val weatherForecast: String,

        @SerialName("workingPeopleNumber")
        val workLaborer: Int,

        @SerialName("workingTime")
        val workHours: Int,

        @SerialName("workingArea")
        val workArea: Int,

        @SerialName("workDtos")
        val workDescriptions: List<WorkDescriptionResponse>,

        @SerialName("imageUrls")
        val images: List<String>,
        val memo: String,
    ) {
        @Serializable
        data class WorkDescriptionResponse(
            @SerialName("workCategory")
            val type: String,

            @SerialName("content")
            val description: String,
        )
    }
}