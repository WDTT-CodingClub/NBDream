package kr.co.wdtt.nbdream

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kr.co.domain.usecase.auth.CheckLoginUseCase
import kr.co.ui.base.BaseViewModel
import kr.co.domain.usecase.auth.FetchAuthUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchAuthUseCase: FetchAuthUseCase,
    private val checkLoginUseCase: CheckLoginUseCase
): BaseViewModel<MainViewModel.State>(savedStateHandle) {
    private val _isAuthorized: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAuthorized = _isAuthorized.asStateFlow()
    init {
        viewModelScopeEH.launch {
            checkLoginUseCase()
        }.invokeOnCompletion {
            viewModelScopeEH.launch {
                fetchAuthUseCase()
                    .distinctUntilChanged()
                    .collectLatest {
                        _isAuthorized.emit(it != null)
                    }
            }
        }
    }
    data object State: BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State = State
}