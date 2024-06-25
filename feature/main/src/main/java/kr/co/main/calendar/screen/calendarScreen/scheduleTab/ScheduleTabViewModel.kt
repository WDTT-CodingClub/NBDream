package kr.co.main.calendar.screen.calendarScreen.scheduleTab

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kr.co.domain.usecase.calendar.GetFarmWorksUseCase
import kr.co.domain.usecase.calendar.GetHolidaysUseCase
import kr.co.main.mapper.calendar.CropModelTypeMapper
import kr.co.main.mapper.calendar.FarmWorkModelMapper
import kr.co.main.mapper.calendar.HolidayModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.FarmWorkModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.ScheduleModel
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

internal interface ScheduleTabEvent {
    fun setCalendarCrop(crop: CropModel)
    fun setCalendarYear(year: Int)
    fun setCalendarMonth(month: Int)

    fun onSelectDate(date: LocalDate)
}


@HiltViewModel
internal class ScheduleTabViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFarmWork: GetFarmWorksUseCase,
    private val getHoliday: GetHolidaysUseCase,
    //private val getSchedule: GetSchedulesUseCase
) : BaseViewModel<ScheduleTabViewModel.ScheduleTabState>(savedStateHandle),
    ScheduleTabEvent {

    private val _calendarCrop = MutableStateFlow<CropModel?>(null)
    private val _calendarYear = MutableStateFlow(LocalDate.now().year)
    private val _calendarMonth = MutableStateFlow(LocalDate.now().monthValue)

    data class ScheduleTabState(
        val calendarCrop: CropModel? = null,
        val calendarYear: Int = LocalDate.now().year,
        val calendarMonth: Int = LocalDate.now().monthValue,
        val selectedDate: LocalDate = LocalDate.now(),

        val farmWorks: List<FarmWorkModel> = emptyList(),
        val holidays: List<HolidayModel> = emptyList(),
        val schedules: List<ScheduleModel> = emptyList()
    ) : State

    val event: ScheduleTabEvent = this@ScheduleTabViewModel

    override fun createInitialState(savedState: Parcelable?): ScheduleTabState {
        //TODO("Not yet implemented")
        return ScheduleTabState()
    }

    init {
        combine(_calendarCrop, _calendarYear, _calendarMonth) { crop, year, month ->
            Timber.d("crop: $crop, year: $year, month: $month")
            getHoliday(
                GetHolidaysUseCase.Params(
                    year = currentState.calendarYear,
                    month = currentState.calendarMonth
                )
            ).let { holidays ->
                updateState {
                    currentState.copy(holidays = holidays.map { HolidayModelMapper.toRight(it) })
                }
                Timber.d("holidays: $holidays")
            }
            crop?.let { crop ->
                Timber.d("crop: $crop, call getFarmWork")
                getFarmWork(
                    GetFarmWorksUseCase.Params(
                        crop = CropModelTypeMapper.toLeft(crop.type),
                        month = month
                    )
                ).let {
                    updateState {
                        copy(farmWorks = it.map { FarmWorkModelMapper.convert(it) })
                    }
                    Timber.d("farmWorks: $it")
                }
//                getSchedule(
//                    GetSchedulesUseCase.Params.Monthly(
//                        crop = CropModelMapper.toLeft(crop).name.koreanName,
//                        year = year,
//                        month = month
//                    )
//                ).collect { schedules ->
//                    updateState {
//                        copy(schedules = schedules.map { ScheduleModelMapper.toRight(it) })
//                    }
//                }
            }
        }.launchIn(viewModelScopeEH)
    }

    override fun setCalendarCrop(crop: CropModel) {
        updateState { copy(calendarCrop = crop) }
        _calendarCrop.update { crop }
    }

    override fun setCalendarYear(year: Int) =
        updateState { copy(calendarYear = year) }

    override fun setCalendarMonth(month: Int) =
        updateState { copy(calendarMonth = month) }

    override fun onSelectDate(date: LocalDate) =
        updateState { copy(selectedDate = date) }

}