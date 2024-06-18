package kr.co.main.calendar.ui.calendarscreen

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.R
import kr.co.main.calendar.ui.calendarscreen.CalendarScreenViewModel.CalendarScreenState
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

internal interface CalendarScreenEvent {
    fun onSelectTab(tab: CalendarScreenState.CalendarTab)
}

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    private val TAG = this@CalendarScreenViewModel::class.java.simpleName

    data class CalendarScreenState(
        val selectedTab: CalendarTab = CalendarTab.SCHEDULE
    ) : State {
        enum class CalendarTab(@StringRes val titleId: Int) {
            SCHEDULE(R.string.feature_main_calendar_tab_title_schedule),
            DIARY(R.string.feature_main_calendar_tab_title_diary)
        }

        override fun toParcelable(): Parcelable? {
            //TODO("serialize")
            return null
        }
    }
    val event = this@CalendarScreenViewModel

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        savedState?.let{
           // TODO("deserialize")
            CalendarScreenState()
        } ?: CalendarScreenState()


    override fun onSelectTab(tab: CalendarScreenState.CalendarTab) {
        updateState { state.value.copy(selectedTab = tab) }
    }
}