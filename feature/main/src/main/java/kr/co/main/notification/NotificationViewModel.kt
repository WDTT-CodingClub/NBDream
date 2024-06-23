package kr.co.main.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<NotificationViewModel.State>(savedStateHandle) {


    fun onCheckedNotification(b: Boolean) {
        updateState {
            copy(setting = b)
        }
    }

    fun onTabSelected(i: Int) {
        updateState {
            copy(selectedTab = i)
        }
    }

    data class State(
        val setting: Boolean = false,

        val selectedTab: Int = 0,

        val notificationList: List<String> = emptyList()
    ) : BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
