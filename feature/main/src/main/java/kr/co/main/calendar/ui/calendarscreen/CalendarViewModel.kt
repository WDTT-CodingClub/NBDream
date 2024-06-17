package kr.co.main.calendar.ui.calendarscreen

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarViewModel.State>(savedStateHandle){
    data class State(
        val dummy: Unit
    ):BaseViewModel.State

    private val TAG = this@CalendarViewModel::class.java.simpleName

    val input = this@CalendarViewModel

    override fun createInitialState(savedState: Parcelable?): State {
        TODO("Not yet implemented")
    }
}