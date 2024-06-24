package kr.co.main.my.setting.verify

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingDeleteSocialVerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase
) : BaseViewModel<MyPageSettingDeleteSocialVerifyViewModel.State>(savedStateHandle) {

    private val _showNextScreen = MutableSharedFlow<Unit>()
    val showNextScreen = _showNextScreen.asSharedFlow()

    fun onVerifyClick() {
        TODO("Not yet implemented")
    }

    init {
        viewModelScopeEH.launch {
            fetchUserUseCase().collectLatest {
                updateState { copy(user = it) }
            }
        }
    }
    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val user: Any? = null
    ) : BaseViewModel.State

}
