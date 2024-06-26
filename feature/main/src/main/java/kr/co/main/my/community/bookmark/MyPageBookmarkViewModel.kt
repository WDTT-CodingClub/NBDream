package kr.co.main.my.community.bookmark

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageBookmarkViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyPageBookmarkViewModel.State>(savedStateHandle){

    data class State(
        val state: Any? = null
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
