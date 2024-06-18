package kr.co.main.calendar.ui.calendar_route.calendar_screen.schedule_tab

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleTabViewModel @Inject constructor(
    stateHandle: SavedStateHandle
):BaseViewModel<ScheduleTabViewModel.State>(stateHandle) {
    data class State(
        val schedules: List<ScheduleModel> = emptyList()
    ):BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State = State()
}