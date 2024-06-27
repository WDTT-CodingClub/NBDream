package kr.co.onboard.welcome

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.entity.UserEntity
import kr.co.domain.usecase.user.RegisterInfoUseCase
import kr.co.domain.usecase.user.SaveUserLocalUseCase
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val registerInfoUseCase: RegisterInfoUseCase,
    private val saveUserLocalUseCase: SaveUserLocalUseCase,
) : BaseViewModel<WelcomeViewModel.State>(saveStateHandle) {

    fun onClickConfirm() = loadingScope {
        UserEntity(
            address = currentState.address,
            bjdCode = currentState.bCode,
            latitude = currentState.latitude,
            longitude = currentState.longitude,
            crops = currentState.crops ?: emptyList()
        ).run {
            registerInfoUseCase(this)
        }
    }.invokeOnCompletion {
        if (it == null)
            viewModelScopeEH.launch {
                saveUserLocalUseCase()
            }
    }

    fun setAddressInfo(
        fullRoadAddress: String,
        bCode: String,
        latitude: Float,
        longitude: Float,
        crops: List<String>,
    ) = updateState {
        copy(
            address = fullRoadAddress,
            bCode = bCode,
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble(),
            crops = crops
        )
    }

    data class State(
        val address: String? = null,
        val bCode: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null,
        val crops: List<String>? = emptyList(),
    ) : BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
