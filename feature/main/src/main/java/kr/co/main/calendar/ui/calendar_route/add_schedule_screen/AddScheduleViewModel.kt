package kr.co.main.calendar.ui.calendar_route.add_schedule_screen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

internal interface AddScheduleScreenInput{
    fun onPostClick()
    fun onCategoryInput(category: ScheduleModel.Category)
    fun onTitleInput(title:String)
    fun onStartDateInput(startDate: LocalDate)
    fun onEndDateInput(endDate: LocalDate)
    fun onSetAlarmInput(setAlarm:Boolean)
    fun onAlarmDateTimeInput(alarmDateTime: LocalDateTime)
    fun onMemoInput(memo:String)
}

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : BaseViewModel<AddScheduleViewModel.State>(stateHandle), AddScheduleScreenInput {
    val input = this@AddScheduleViewModel

    override fun createInitialState(savedState: Parcelable?): State {
        TODO("Not yet implemented")
    }

    override fun onPostClick() {
        TODO("Not yet implemented")
    }

    override fun onCategoryInput(category: ScheduleModel.Category) {
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

    data class State(
        val category: ScheduleModel.Category = ScheduleModel.Category.All
    ) : BaseViewModel.State
}