package kr.co.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kr.co.common.model.CustomErrorType
import kr.co.common.model.CustomException

abstract class BaseViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorType =
            if (throwable is CustomException) throwable.customError
            else CustomErrorType.UNKNOWN

        onError(errorType)
        setLoading(false)
    }

    protected val viewModelScopeEH = viewModelScope + exceptionHandler

    private val _error: MutableSharedFlow<CustomErrorType> = MutableSharedFlow()
    val error = _error.asSharedFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private fun setLoading(isLoading: Boolean) = viewModelScope.launch {
        _isLoading.emit(isLoading)
    }

    protected fun loadingScope(
        action: suspend CoroutineScope.() -> Unit
    ) = viewModelScopeEH.launch {
        setLoading(true)
        action(this)
        setLoading(false)
    }

    private fun onError(errorType: CustomErrorType) = viewModelScope.launch{
        _error.emit(errorType)
    }


    protected fun <T : Any> Flow<T>.bindState(to: MutableStateFlow<T>) {
        onEach(to::emit).launchIn(viewModelScopeEH)
    }

    protected fun <T : Any> Flow<T>.bindShared(to: MutableSharedFlow<T>) {
        onEach(to::emit).launchIn(viewModelScopeEH)
    }
}

