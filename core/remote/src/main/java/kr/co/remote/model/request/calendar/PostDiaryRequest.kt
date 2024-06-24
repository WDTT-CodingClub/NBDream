package kr.co.remote.model.request.calendar

internal data class PostDiaryRequest (
    val date: String,
    val holidayList: List<HolidayRequest>,
    val weatherForecast: String,
    val workLaborer: Int,
    val workHours: Int,
    val workArea: Int,
    val workDescriptions: List<WorkDescriptionRequest>,
    val memo: String
){
    data class HolidayRequest(
        val date: String,
        val isHoliday: String,
        val type: String,
        val name: String
    )
    data class WorkDescriptionRequest(
        val type: String,
        val description: String
    )
}