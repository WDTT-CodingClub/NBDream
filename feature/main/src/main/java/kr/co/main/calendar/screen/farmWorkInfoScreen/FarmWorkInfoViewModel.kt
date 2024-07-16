package kr.co.main.calendar.screen.farmWorkInfoScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class FarmWorkInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<FarmWorkInfoViewModel.FarmWorkInfoScreenState>(savedStateHandle) {
    data class FarmWorkInfoScreenState(
        val title: String = "",
        val videoUrl: String = ""
    ) : State

    override fun createInitialState(savedState: Parcelable?) = FarmWorkInfoScreenState()

    init {
        with(savedStateHandle) {
            get<String>(CalendarNavGraph.ARG_FARM_WORK_TITLE)?.let { title ->
                updateState { copy(title = title) }
            }
            get<String>(CalendarNavGraph.ARG_FARM_WORK_VIDEO_URL)?.let { videoUrl ->
                updateState { copy (videoUrl = videoUrl) }
            }
        }
    }
}