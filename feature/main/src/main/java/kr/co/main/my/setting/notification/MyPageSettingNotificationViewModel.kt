package kr.co.main.my.setting.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.usecase.alarm.AlarmFetchUseCase
import kr.co.domain.usecase.alarm.AlarmUpdateUseCase
import kr.co.main.notification.NotificationViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingNotificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUseCase: AlarmFetchUseCase,
    private val updateUseCase: AlarmUpdateUseCase
) : BaseViewModel<MyPageSettingNotificationViewModel.State>(savedStateHandle) {

    private val isPermissionGranted: Boolean = savedStateHandle.get<Boolean>("grant") ?: false
    private val _navigationEffects = MutableSharedFlow<NotificationViewModel.NotificationViewEffect>()
    val navigationEffects = _navigationEffects.asSharedFlow()

    init {
        fetchNotification()
    }

    override fun createInitialState(savedState: Parcelable?) = State()

    fun updateCommentAlarm(newAlarm: Boolean) {
        if (isPermissionGranted || !newAlarm) {
            updateAlarmState(commentAlarm = newAlarm)
        } else {
            showPermissionDialog()
        }
    }

    fun updateScheduleAlarm(newAlarm: Boolean) {
        if (isPermissionGranted || !newAlarm) {
            updateAlarmState(scheduleAlarm = newAlarm)
        } else {
            showPermissionDialog()
        }
    }

    private fun updateAlarmState(commentAlarm: Boolean? = null, scheduleAlarm: Boolean? = null) {
        updateState {
            copy(
                commentAlarm = commentAlarm ?: this.commentAlarm,
                scheduleAlarm = scheduleAlarm ?: this.scheduleAlarm
            )
        }
        updateNotification()
    }

    private fun fetchNotification() = loadingScope {
        fetchUseCase().let {
            updateState {
                copy(
                    commentAlarm = it.commentAlarm,
                    scheduleAlarm = it.scheduleAlarm
                )
            }
        }
    }

    private fun updateNotification() = loadingScope {
        val updatedStatus = updateUseCase(
            AlarmUpdateUseCase.Params(
                currentState.commentAlarm ?: false,
                currentState.scheduleAlarm ?: false
            )
        )
        updateState {
            copy(
                commentAlarm = updatedStatus.commentAlarm,
                scheduleAlarm = updatedStatus.scheduleAlarm
            )
        }
    }

    private fun showPermissionDialog() {
        updateState {
            copy(showPermissionDialog = true)
        }
    }

    fun onPermissionGrantedClick() {
        viewModelScope.launch {
            _navigationEffects.emit(NotificationViewModel.NotificationViewEffect.NavigateToAppSettings)
        }
    }

    fun onPermissionDialogDismiss() {
        updateState {
            copy(showPermissionDialog = false)
        }
    }

    data class State(
        val commentAlarm: Boolean? = false,
        val scheduleAlarm: Boolean? = false,
        val showPermissionDialog: Boolean = false
    ) : BaseViewModel.State
}
