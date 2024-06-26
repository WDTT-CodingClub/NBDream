package kr.co.main.my.community

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageWriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyPageWriteViewModel.State>(savedStateHandle){

    data class State(
        val state: Any? = null
    ) : BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
