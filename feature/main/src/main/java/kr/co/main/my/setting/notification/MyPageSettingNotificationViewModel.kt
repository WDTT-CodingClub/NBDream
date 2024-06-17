package kr.co.main.my.setting.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingNotificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyPageSettingNotificationViewModel.State>(savedStateHandle) {

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val state: Any? = null,
    ) : BaseViewModel.State
}
