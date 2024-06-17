package kr.co.main.my.setting.delete

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingDeleteAccountViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyPageSettingDeleteAccountViewModel.State>(savedStateHandle) {

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State (
        val state: Any? = null
    ) : BaseViewModel.State

}
