package kr.co.main.calendar.ui.calendartab.scheduletab

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
        val schedules: List<ScheduleModel>
    ):BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State {
        TODO("Not yet implemented")
    }
}