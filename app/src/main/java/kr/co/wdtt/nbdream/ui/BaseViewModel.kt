package kr.co.wdtt.nbdream.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
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