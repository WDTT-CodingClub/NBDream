package kr.co.main.calendar.ui.calendar_screen.calendar_screen.diary_tab

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryTabViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel<DiaryTabViewModel.State>(stateHandle) {
    data class State(
        val diaries: List<DiaryModel> = emptyList()
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State = State()
}