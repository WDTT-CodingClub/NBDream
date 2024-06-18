package kr.co.main.calendar.ui.calendar_route.calendar_screen

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.R
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.ui.calendar_route.calendar_screen.CalendarScreenViewModel.CalendarScreenState
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface CalendarScreenEvent {
    fun onSelectTab(tab: CalendarScreenState.CalendarTab)
    fun onSelectYear(year:Int)
    fun onSelectMonth(month:Int)
    fun onSelectCrop(crop:CropModel)
}

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    private val TAG = this@CalendarScreenViewModel::class.java.simpleName

    data class CalendarScreenState(
        val userCrops: List<CropModel> = emptyList(),
        val selectedTab: CalendarTab = CalendarTab.SCHEDULE,
        val calendarYear:Int = LocalDate.now().year,
        val calendarMonth:Int = LocalDate.now().monthValue,
        val calendarCrop: CropModel? = if(userCrops.isNotEmpty()) userCrops.first() else null
    ) : State {
        enum class CalendarTab(@StringRes val titleId: Int) {
            SCHEDULE(R.string.feature_main_calendar_tab_title_schedule),
            DIARY(R.string.feature_main_calendar_tab_title_diary)
        }

        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
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

    override fun onSelectYear(year: Int) {
        updateState { state.value.copy(calendarYear = year) }
    }

    override fun onSelectMonth(month: Int) {
        updateState { state.value.copy(calendarMonth = month) }
    }

    override fun onSelectCrop(crop: CropModel) {
        updateState { state.value.copy(calendarCrop = crop) }
    }
}