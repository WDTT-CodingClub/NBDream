package kr.co.remote.model.request.calendar

internal data class UpdateDiaryRequest (
    val id:Int,
    val crop:String,
    val date: String,
    val holidayList: List<PostDiaryRequest.HolidayRequest>,
    val weatherForecast: String,
    val workLaborer: Int,
    val workHours: Int,
    val workArea: Int,
    val workDescriptions: List<PostDiaryRequest.WorkDescriptionRequest>,
    val memo: String
)