package kr.co.main.accountbook.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class AccountBookViewModel @Inject constructor(

) : BaseViewModel() {
    override fun createInitialState(): State = State()

    data class State(
        val state: Any? = null
    ): BaseViewModel.State
}