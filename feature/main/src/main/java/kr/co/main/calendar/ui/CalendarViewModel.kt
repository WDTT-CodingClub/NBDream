package kr.co.main.calendar.ui

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.entity.HolidayEntity
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.FarmWorkModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject


interface CalendarScreenInput {
    fun onSelectCrop(crop: CropModel)
    fun onSelectMonth(month: Int)

    fun onAddScheduleClick()
    fun onAddDiaryClick()
}
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarViewModel.State>(savedStateHandle), CalendarScreenInput {
    private val TAG = this@CalendarViewModel::class.java.simpleName

    val input = this@CalendarViewModel

    override fun createInitialState(savedState: Parcelable?): State {
        TODO("Not yet implemented")
    }


    override fun onSelectCrop(crop: CropModel) {
        TODO("Not yet implemented")
    }

    override fun onSelectMonth(month: Int) {
        // TODO 캘린더 년도 & 월 변경
    }

    override fun onAddScheduleClick() {
        // TODO 일정 추가화면으로 이동
    }

    override fun onAddDiaryClick() {
        // TODO 영농일지 추가화면으로 이동
    }


    data class State (
        val userCrops: List<CropModel> = emptyList(),
        val selectedCrop: CropModel? = null,

        val year: Int = LocalDate.now().year,
        val month: Int = LocalDate.now().monthValue,

        val farmWorks: List<FarmWorkModel> = emptyList(),
        val holidays: List<HolidayEntity> = emptyList(),
        val schedules: List<ScheduleModel> = emptyList(),
        val diaries: List<DiaryModel> = emptyList()
    ): BaseViewModel.State
}