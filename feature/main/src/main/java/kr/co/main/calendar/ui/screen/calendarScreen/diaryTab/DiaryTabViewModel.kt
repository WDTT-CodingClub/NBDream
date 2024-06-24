package kr.co.main.calendar.ui.screen.calendarScreen.diaryTab

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface DiaryTabEvent {
    fun onSelectDate(date: LocalDate)
}

@HiltViewModel
internal class DiaryTabViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : BaseViewModel<DiaryTabViewModel.State>(stateHandle),
    DiaryTabEvent {

    data class State(
        val selectedDate: LocalDate = LocalDate.now(),
        val diaries: List<DiaryModel> = emptyList()
    ) : BaseViewModel.State
    val event: DiaryTabEvent = this@DiaryTabViewModel

    override fun createInitialState(savedState: Parcelable?): State = State()
    override fun onSelectDate(date: LocalDate) {
        updateState { copy(selectedDate = date) }
    }
}