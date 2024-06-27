package kr.co.main.model.calendar.type

internal enum class CalendarSortType(val koreanName: String) {
    RECENCY(
        koreanName = "최신순"
    ),
    OLDEST(
        koreanName = "과거순"
    ),
}