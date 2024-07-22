package kr.co.main.notification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.usecase.alarm.CheckAlarmHistoryUseCase
import kr.co.domain.usecase.alarm.DeleteAlarmHistoryUseCase
import kr.co.domain.usecase.alarm.GetAlarmHistoryUseCase
import kr.co.ui.base.BaseViewModel
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
                    createdDate = alarm.createdDate
                )
            }
            updateAlarmHistoryList(alarmHistories)
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
            val createdDate: String
        )
    }

    override fun createInitialState(savedState: Parcelable?) = State()
}
