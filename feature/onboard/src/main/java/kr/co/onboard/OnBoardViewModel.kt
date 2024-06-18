package kr.co.onboard

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.domain.model.AuthType
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.domain.usecase.auth.LoginUseCase
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class OnBoardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val socialLoginProvider: SocialLoginProvider,
    private val loginUseCase: LoginUseCase
) : BaseViewModel<OnBoardViewModel.State>(savedStateHandle) {
    fun onSocialLoginClick(authType: AuthType) {
        viewModelScopeEH.launch {
            Timber.d(socialLoginProvider.login(authType).toString())

            socialLoginProvider.login(authType).let {
                loginUseCase(LoginUseCase.Params(it.type,it.token))
            }
        }
    }

    init {
        viewModelScope.launch {
            error.collectLatest {
                Timber.d(it.cause?.message)
            }
        }
    }

    override fun createInitialState(savedState: Parcelable?) = State

    data object State : BaseViewModel.State
}
