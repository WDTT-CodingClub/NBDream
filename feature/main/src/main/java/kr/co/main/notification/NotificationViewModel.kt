package kr.co.main.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<NotificationViewModel.State>(savedStateHandle) {
    init {
        fetchHistories()
    }

    private fun updateAlarmHistoryList(alarmHistoryList: List<State.AlarmHistory>) {
        updateState {
            copy(alarmHistories = alarmHistoryList)
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

    fun onCheckedNotification(b: Boolean) {
        updateState {
            copy(setting = b)
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
        val alarmHistories: List<AlarmHistory> = emptyList()
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

    override fun createInitialState(savedState: Parcelable?) = State()
}
