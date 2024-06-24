package kr.co.main.calendar.ui.calendarScreen.addScheduleScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

internal interface AddScheduleScreenEvent {
    fun onBackClick()
    fun onPostClick()

    fun onTypeInput(type: ScheduleModelType)
    fun onTitleInput(title: String)

    fun onStartDateInput(startDate: LocalDate)
    fun onEndDateInput(endDate: LocalDate)

    fun onSetAlarmInput(setAlarm: Boolean)
    fun onAlarmDateTimeInput(alarmDateTime: LocalDateTime)

    fun onMemoInput(memo: String)
}

@HiltViewModel
internal class AddScheduleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddScheduleViewModel.AddScheduleScreenState>(savedStateHandle),
    AddScheduleScreenEvent {
    val event: AddScheduleScreenEvent = this@AddScheduleViewModel

    data class AddScheduleScreenState(
        val calendarCrop: CropModel? = null,
        val scheduleType: ScheduleModelType = ScheduleModelType.All
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO ("serialize")
            return null
        }
    }

    override fun createInitialState(savedState: Parcelable?): AddScheduleScreenState =
        savedState?.let {
            // TODO ("deserialize")
            AddScheduleScreenState()
        } ?: AddScheduleScreenState()

    init {
        savedStateHandle.get<Int>(CalendarNavGraph.ARG_CROP_NAME_ID)?.let { cropNameId ->
            updateState {
                copy(calendarCrop = CropModel.create(CropModelType.ofValue(cropNameId)))
            }
        }
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onPostClick() {
        TODO("Not yet implemented")
    }

    override fun onTypeInput(category: ScheduleModelType) {
        TODO("Not yet implemented")
    }

    override fun onTitleInput(title: String) {
        TODO("Not yet implemented")
    }

    override fun onStartDateInput(startDate: LocalDate) {
        TODO("Not yet implemented")
    }

    override fun onEndDateInput(endDate: LocalDate) {
        TODO("Not yet implemented")
    }

    override fun onSetAlarmInput(setAlarm: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAlarmDateTimeInput(alarmDateTime: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun onMemoInput(memo: String) {
        TODO("Not yet implemented")
    }
}