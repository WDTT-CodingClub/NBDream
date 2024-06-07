package kr.co.onboard.ui

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.model.AuthType
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class OnBoardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val socialLoginUseCase: SocialLoginProvider
): BaseViewModel<OnBoardViewModel.State>(savedStateHandle) {
    fun onSocialLoginClick(authType: AuthType) {
        viewModelScopeEH.launch {

        }
    }
    init {

    }
    override fun createInitialState(savedState: Parcelable?) = State

    data object State: BaseViewModel.State
}
