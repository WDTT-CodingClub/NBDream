package kr.co.ui.base

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kr.co.common.model.CustomErrorType
import kr.co.common.model.CustomException

abstract class BaseViewModel<STATE: BaseViewModel.State>(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorType =
            if (throwable is CustomException) throwable.customError
            else CustomErrorType.UNKNOWN

        onError(errorType)
        setLoading(false)
    }

    private val initialState: STATE by lazy {
        createInitialState(savedStateHandle[KEY_STATE])
    }

    private var _currentState: STATE? = null
        set(value) {
            field = value
            savedStateHandle[KEY_STATE] = value?.toParcelable()
        }

    val currentState: STATE
        get() {
            return _currentState ?: initialState
        }

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

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

    private fun onError(errorType: CustomErrorType?) = viewModelScope.launch{
        errorType?.let { _error.emit(it) }
    }


    protected fun <T : Any> Flow<T>.bindState(to: MutableStateFlow<T>) {
        onEach(to::emit).launchIn(viewModelScopeEH)
    }

    protected fun <T : Any> Flow<T>.bindShared(to: MutableSharedFlow<T>) {
        onEach(to::emit).launchIn(viewModelScopeEH)
    }

    protected fun updateState(action: STATE.() -> STATE) {
        try {
            _state.updateAndGet(action)
                .also {
                    _currentState = it
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected inline fun <reified T: STATE, K : Any?> Flow<T>.select(
        crossinline selector: (state:T) -> K,
    ): Flow<K> {
        return this.distinctUntilChangedBy { state: T -> selector(state) }
            .map { state: T -> selector(state) }
    }

    protected abstract fun createInitialState(savedState: Parcelable?): STATE

    interface State {
        fun toParcelable(): Parcelable? = null
    }

    companion object {
        private const val KEY_STATE = "state"
    }
}

