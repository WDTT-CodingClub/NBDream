package kr.co.wdtt.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.io.IOException

abstract class BaseViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorType =
            if (throwable is CustomException) throwable.customError
            else CustomErrorType.UN_KNOWN

        viewModelScope.launch { onError(errorType) }
    }

    abstract suspend fun onError(errorType: CustomErrorType)

    protected val viewModelScopeEH = viewModelScope + exceptionHandler

    protected fun  <T: Any> Flow<T>.bindState(to: MutableStateFlow<T>) {
        onEach(to::emit).launchIn(viewModelScopeEH)
    }

    protected fun  <T: Any> Flow<T>.bindShared(to: MutableSharedFlow<T>) {
        onEach(to::emit).launchIn(viewModelScopeEH)
    }
}

class CustomException(message: String?, val customError: CustomErrorType) : IOException(message)

enum class CustomErrorType(val errorCode: Int) {
    CONNECTION(40),
    IO(41),
    UN_KNOWN(43)
    ;

    companion object {
        val customError = entries
    }
}