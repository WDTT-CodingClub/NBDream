package kr.co.main.my.setting

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.usecase.auth.LogoutUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val logoutUseCase: LogoutUseCase
): BaseViewModel<MyPageSettingViewModel.State>(saveStateHandle) {

    fun onLogout() = viewModelScopeEH.launch {
        logoutUseCase()
    }

    data object State: BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State
}
