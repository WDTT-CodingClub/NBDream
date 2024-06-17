package kr.co.main.my.profile

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MyPageProfileEditViewModel.State>(savedStateHandle) {

    init {

    }

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val state: Any? = null,
    ) : BaseViewModel.State
}
