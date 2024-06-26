package kr.co.main.model.calendar.type

import androidx.annotation.StringRes
import kr.co.main.R

enum class CalendarTabType(
    val pagerIndex: Int,
    @StringRes val titleId: Int
) {
    SCHEDULE(0, R.string.feature_main_calendar_tab_title_schedule),
    DIARY(1, R.string.feature_main_calendar_tab_title_diary);

    companion object{
        fun ofIndex(pagerIndex: Int) = when (pagerIndex) {
            SCHEDULE.pagerIndex -> SCHEDULE
            DIARY.pagerIndex -> DIARY
            else -> throw IllegalStateException("pagerState.currentPage is not valid")
        }
    }
}