package kr.co.data.model.data.calendar

data class DiaryData(
    val id: Int,
    val date: String,
    val holidayList: List<HolidayData> = emptyList(),
    val weatherForecast: String,
    val workLaborer: Int = 0,
    val workHours: Int = 0,
    val workArea: Int = 0,
    val workDescriptions: List<WorkDescriptionData> = emptyList(),
    val images: List<String> = emptyList(),
    val memo: String = ""
) {
    data class WorkDescriptionData(
        val type: String,
        val description: String,
    )
}
