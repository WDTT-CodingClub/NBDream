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

    fun onSelectChange(e: Int?) = updateState {
        copy(select = e)
    }

    fun onReasonChange(e: String) = updateState {
        copy(reason = e)
    }

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State (
        val select: Int? = null,
        val reason: String? = null
    ) : BaseViewModel.State {
        val isSelectValid: Boolean
            get() = select != null
    }

}
