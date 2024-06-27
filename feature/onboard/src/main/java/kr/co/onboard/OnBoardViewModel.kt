package kr.co.onboard

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.domain.entity.type.AuthType
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.domain.usecase.auth.LoginUseCase
import kr.co.domain.usecase.user.SaveUserLocalUseCase
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class OnBoardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val socialLoginProvider: SocialLoginProvider,
    private val loginUseCase: LoginUseCase,
    private val saveUserLocalUseCase: SaveUserLocalUseCase,
) : BaseViewModel<OnBoardViewModel.State>(savedStateHandle) {

    private val _showAddressScreen = MutableSharedFlow<Unit>()

    val showAddressScreen = _showAddressScreen.asSharedFlow()

    fun onSocialLoginClick(authType: AuthType) {
        viewModelScopeEH.launch {
            socialLoginProvider.login(authType).let {
                loginUseCase(LoginUseCase.Params(it.type, it.token))
                    .run {
                        when (this) {
                            200 -> {
                                saveUserLocalUseCase()
                            }

                            201 -> {
                                _showAddressScreen.emit(Unit)
                            }
                        }
                    }
            }
        }
    }

    init {
    }

    override fun createInitialState(savedState: Parcelable?) = State

    data object State : BaseViewModel.State
}
