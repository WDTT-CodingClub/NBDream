package kr.co.main.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.usecase.alarm.AlarmFetchUseCase
import kr.co.domain.usecase.alarm.AlarmUpdateUseCase
import kr.co.domain.usecase.alarm.CheckAlarmHistoryUseCase
import kr.co.domain.usecase.alarm.DeleteAlarmHistoryUseCase
import kr.co.domain.usecase.alarm.GetAlarmHistoryUseCase
import kr.co.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val getAlarmHistoryUseCase: GetAlarmHistoryUseCase,
    private val checkAlarmHistoryUseCase: CheckAlarmHistoryUseCase,
    private val deleteAlarmHistoryUseCase: DeleteAlarmHistoryUseCase,
    private val alarmFetchUseCase: AlarmFetchUseCase,
    private val alarmUpdateUseCase: AlarmUpdateUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<NotificationViewModel.State>(savedStateHandle) {
    private val isPermissionGranted: Boolean = savedStateHandle.get<Boolean>("grant") ?: false
    private val _navigationEffects = MutableSharedFlow<NotificationViewEffect>()
    val navigationEffects = _navigationEffects.asSharedFlow()

    init {
        fetchAlarmStatus()
        fetchHistories()
    }

    private fun updateAlarmHistoryList(alarmHistoryList: List<State.AlarmHistory>) {
        updateState {
            copy(alarmHistories = alarmHistoryList)
        }
    }

    private fun fetchAlarmStatus() {
        loadingScope {
            val alarmStatus = alarmFetchUseCase()
            val setting = alarmStatus.commentAlarm && alarmStatus.scheduleAlarm
            updateState {
                copy(setting = setting)
            }

            if (setting) {
                updateAlarmSettings()
            }
        }
    }

    private fun fetchHistories() {
        loadingScope {
            val alarmHistories = getAlarmHistoryUseCase().map { alarm ->
                State.AlarmHistory(
                    id = alarm.id,
                    alarmType = alarm.alarmType,
                    title = alarm.title,
                    content = alarm.content,
                    checked = alarm.checked,
                    createdDate = alarm.createdDate,
                    targetId = alarm.targetId
                )
            }.sortedByDescending { alarm ->
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(alarm.createdDate)
            }
            updateAlarmHistoryList(alarmHistories)
        }.invokeOnCompletion {
            if (it == null) {
                checkUncheckedAlarmHistories()
            }
        }
    }

    private fun checkUncheckedAlarmHistories() {
        val uncheckedAlarmIds = currentState.alarmHistories
            .filterNot { it.checked }
            .map { it.id }

        if (uncheckedAlarmIds.isNotEmpty()) {
            loadingScope {
                checkAlarmHistoryUseCase(CheckAlarmHistoryUseCase.Params(uncheckedAlarmIds))
            }
        }
    }

    fun onCheckedNotification(isChangedStatus: Boolean) {
        if (isChangedStatus && !isPermissionGranted) {
            updateState {
                copy(showPermissionDialog = true)
            }
        } else {
            updateState {
                copy(setting = isChangedStatus)
            }

            if (isChangedStatus) {
                updateAlarmSettings()
            }
        }
    }

    private fun updateAlarmSettings() {
        loadingScope {
            alarmUpdateUseCase(AlarmUpdateUseCase.Params(commentAlarm = true, scheduleAlarm = true))
        }.invokeOnCompletion {

        }
    }

    fun onPermissionGrantedClick() {
        viewModelScope.launch {
            _navigationEffects.emit(NotificationViewEffect.NavigateToAppSettings)
        }
    }

    fun onPermissionDialogDismiss() {
        updateState {
            copy(showPermissionDialog = false)
        }
    }

    fun onTabSelected(i: Int) {
        updateState {
            copy(selectedTab = i)
        }
    }

    fun onDeleteClicked(id: Long? = null) {
        loadingScope {
            if (id == null) {
                val allIds = currentState.alarmHistories.map { it.id }
                deleteAlarmHistoryUseCase(DeleteAlarmHistoryUseCase.Params(ids = allIds))
            } else {
                deleteAlarmHistoryUseCase(DeleteAlarmHistoryUseCase.Params(ids = listOf(id)))
            }
        }.invokeOnCompletion {
            if (it == null) {
                fetchHistories()
            }
        }
    }

    data class State(
        val setting: Boolean = false,
        val selectedTab: Int = 0,
        val notificationList: List<String> = emptyList(),
        val alarmHistories: List<AlarmHistory> = emptyList(),
        val showPermissionDialog: Boolean = false
    ) : BaseViewModel.State {
        data class AlarmHistory(
            val id: Long,
            val alarmType: String,
            val title: String,
            val content: String,
            val checked: Boolean,
            val createdDate: String,
            val targetId: Long
        )
    }

    sealed class NotificationViewEffect {
        data object NavigateToAppSettings : NotificationViewEffect()
    }

    override fun createInitialState(savedState: Parcelable?) = State()
}
