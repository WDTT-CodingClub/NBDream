package kr.co.main.my.community

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageCommunityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyPageCommunityViewModel.State>(savedStateHandle){

    data class State(
        val state: Any? = null
    ) : BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
