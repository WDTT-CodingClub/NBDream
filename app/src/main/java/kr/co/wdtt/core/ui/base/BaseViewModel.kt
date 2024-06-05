package kr.co.wdtt.core.ui.base

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
import java.io.IOException

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

class CustomException(message: String?, val customError: CustomErrorType) : IOException(message)

enum class CustomErrorType(val errorCode: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    PROXY_AUTHENTICATION_REQUIRED(407),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    LENGTH_REQUIRED(411),
    PRECONDITION_FAILED(412),
    REQUEST_ENTITY_TOO_LARGE(413),
    REQUEST_URI_TOO_LARGE(414),
    UNSUPPORTED_MEDIA_TYPE(415),
    RANGE_NOT_SATISFIABLE(416),
    EXPECTATION_FAILED(417),
    UNPROCESSABLE_ENTITY(422),
    LOCKED(423),
    FAILED_DEPENDENCY(424),
    UPGRADE_REQUIRED(426),
    PRECONDITION_REQUIRED(428),
    TOO_MANY_REQUESTS(429),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431),
    CONNECTION_CLOSED_WITHOUT_RESPONSE(444),
    UNAVAILABLE_FOR_LEGAL_REASONS(451),
    UNKNOWN(-1)
    ;

    companion object {
        val customError = entries.associateBy(CustomErrorType::errorCode)
    }
}