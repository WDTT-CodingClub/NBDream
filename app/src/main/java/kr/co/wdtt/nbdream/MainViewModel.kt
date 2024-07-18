package kr.co.wdtt.nbdream

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.domain.usecase.auth.CheckLoginUseCase
import kr.co.ui.base.BaseViewModel
import kr.co.domain.usecase.auth.FetchAuthUseCase
import kr.co.domain.usecase.fcm.FetchFcmTokenUseCase
import kr.co.domain.usecase.fcm.SaveFcmTokenUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchAuthUseCase: FetchAuthUseCase,
    private val checkLoginUseCase: CheckLoginUseCase,
    private val saveFcmTokenUseCase: SaveFcmTokenUseCase,
    private val fetchFcmTokenUseCase: FetchFcmTokenUseCase
) : BaseViewModel<MainViewModel.State>(savedStateHandle) {
    private val _isAuthorized: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAuthorized = _isAuthorized.asStateFlow()

    init {
        viewModelScopeEH.launch {
            checkLoginUseCase()
            delay(SPLASH_DELAY)
        }.invokeOnCompletion {
            viewModelScopeEH.launch {
                fetchAuthUseCase()
                    .distinctUntilChanged()
                    .collectLatest {
                        _isAuthorized.emit(it != null)
                        if (it != null) {
                            fetchAndSaveFcmToken()
                        }
                    }
            }
        }
    }

    private suspend fun getFcmToken(): String? {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            token
        } catch (e: Exception) {
            Timber.d("Fetching FCM registration token failed", e)
            null
        }
    }

    private fun fetchAndSaveFcmToken() {
        viewModelScopeEH.launch {
            val localToken = fetchFcmTokenUseCase(Unit).firstOrNull()
            val newToken = getFcmToken()
            if (newToken != null && localToken != newToken) {
                saveFcmTokenUseCase(newToken)
            }
        }
    }

    data object State : BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State = State

    private companion object {
        const val SPLASH_DELAY = 2_000L
    }
}