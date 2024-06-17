package kr.co.onboard.address

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class InputAddressViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): BaseViewModel<InputAddressViewModel.State>(savedStateHandle) {
    data class State(
        val fullRoadAddr: String = "",
        val jibunAddr: String = ""
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    fun onFullRoadAddrChange(newAddr: String) {
        updateState { copy(fullRoadAddr = newAddr) }
    }

    fun onJibunAddrChange(newAddr: String) {
        updateState { copy(jibunAddr = newAddr) }
    }

}