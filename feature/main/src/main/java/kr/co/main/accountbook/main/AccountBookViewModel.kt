package kr.co.main.accountbook.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class AccountBookViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookViewModel.State>(savedStateHandle) {

    init {

    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val state: Any? = null
    ): BaseViewModel.State
}