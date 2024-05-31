package kr.co.wdtt.nbdream.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kr.co.wdtt.core.ui.base.BaseViewModel
import kr.co.wdtt.nbdream.domain.usecase.auth.FetchAuthUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchAuthUseCase: FetchAuthUseCase
): BaseViewModel() {
    private val _isAuthorized: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthorized = _isAuthorized.asStateFlow()
    init {
        viewModelScopeEH.launch {
            fetchAuthUseCase()
                .distinctUntilChanged()
                .collectLatest {
                    delay(SPLASH_DURATION)
                    _isAuthorized.emit(it != null)
                }
        }
    }

    companion object {
        private const val SPLASH_DURATION = 2_000L
    }
}