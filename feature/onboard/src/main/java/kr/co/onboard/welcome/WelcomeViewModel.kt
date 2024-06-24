package kr.co.onboard.welcome

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.usecase.user.SaveUserLocalUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val saveUserLocalUseCase: SaveUserLocalUseCase
) : BaseViewModel<WelcomeViewModel.State>(saveStateHandle){

    fun onClickNext() = loadingScope {
        saveUserLocalUseCase.invoke()
    }

    data object State: BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State
}
