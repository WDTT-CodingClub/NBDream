package kr.co.main.my.setting.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.usecase.notification.AlarmFetchUseCase
import kr.co.domain.usecase.notification.AlarmUpdateUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingNotificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUseCase: AlarmFetchUseCase,
    private val updateUseCase: AlarmUpdateUseCase
) : BaseViewModel<MyPageSettingNotificationViewModel.State>(savedStateHandle) {

    init {
        fetchNotification()
    }

    override fun createInitialState(savedState: Parcelable?) = State()

    fun updateCommentAlarm(newAlarm: Boolean) {
        updateAlarmState(commentAlarm = newAlarm)
    }

    fun updateScheduleAlarm(newAlarm: Boolean) {
        updateAlarmState(scheduleAlarm = newAlarm)
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

    data class State(
        val commentAlarm: Boolean? = false,
        val scheduleAlarm: Boolean? = false
    ) : BaseViewModel.State
}
