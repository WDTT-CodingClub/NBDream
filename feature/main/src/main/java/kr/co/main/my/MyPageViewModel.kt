package kr.co.main.my

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyPageViewModel.State>(savedStateHandle) {

    init {

    }
    override fun createInitialState(savedState: Parcelable?) = State()
    data class State(
        val state : Any? = null
    ) : BaseViewModel.State
}
