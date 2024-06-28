package kr.co.main.calendar.screen.calendarScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.GetDiariesUseCase
import kr.co.domain.usecase.calendar.GetFarmWorksUseCase
import kr.co.domain.usecase.calendar.GetHolidaysUseCase
import kr.co.domain.usecase.calendar.GetSchedulesUseCase
import kr.co.domain.usecase.calendar.GetUserCropsUseCase
import kr.co.main.mapper.calendar.CropModelMapper
import kr.co.main.mapper.calendar.CropModelTypeMapper
import kr.co.main.mapper.calendar.DiaryModelMapper
import kr.co.main.mapper.calendar.FarmWorkModelMapper
import kr.co.main.mapper.calendar.HolidayModelMapper
import kr.co.main.mapper.calendar.ScheduleModelMapper
import kr.co.main.mapper.calendar.ScheduleModelTypeMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.FarmWorkModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.ScheduleModel
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

internal interface CalendarScreenEvent {
    fun onYearSelect(year: Int)
    fun onMonthSelect(month: Int)
    fun onCropSelect(crop: CropModel)
    fun onDateSelect(date: LocalDate)
}

@HiltViewModel
internal class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserCrops: GetUserCropsUseCase,
    private val getFarmWorks: GetFarmWorksUseCase,
    private val getHolidays: GetHolidaysUseCase,
    private val getSchedules: GetSchedulesUseCase,
    private val getDiaries: GetDiariesUseCase
) : BaseViewModel<CalendarScreenViewModel.CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    val event: CalendarScreenEvent = this@CalendarScreenViewModel

    private val _initialized = MutableStateFlow(savedStateHandle.get<Boolean>("init") ?: true)
    val initialized = _initialized.asSharedFlow()

    data class CalendarScreenState(
        val userCrops: List<CropModel> = emptyList(),
        val crop: CropModel? = null,
        val year: Int = LocalDate.now().year,
        val month: Int = LocalDate.now().monthValue,

        val selectedDate: LocalDate = LocalDate.now(),

        val farmWorks: List<FarmWorkModel> = emptyList(),
        val holidays: List<HolidayModel> = emptyList(),
        val allSchedules: List<ScheduleModel> = emptyList(),
        val cropSchedules: List<ScheduleModel> = emptyList(),
        val diaries: List<DiaryModel> = emptyList()
    ) : State

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        CalendarScreenState()

    init {
        Timber.d("init) called")
        viewModelScopeEH.launch {
            initialized.collect {
                initialize()
            }
        }

        viewModelScopeEH.launch {
            error.collect {
                Timber.e("error: ${it.throwable?.message}\n${it.customError}\n${it.throwable?.cause}")
            }
        }

        updateUserCrops()
        updateHolidays()
        updateAllSchedules()
    }

    fun initialize() {
        updateAllSchedules()
        updateCropSchedules()
        updateDiaries()
    }

    override fun onYearSelect(year: Int) {
        Timber.d("onYearSelect) year: $year")
        updateYear(year)
    }

    override fun onMonthSelect(month: Int) {
        Timber.d("onMonthSelect) month: $month")
        updateMonth(month)
    }

    override fun onCropSelect(crop: CropModel) {
        Timber.d("onCropSelect) crop: $crop")
        updateCrop(crop)
    }

    override fun onDateSelect(date: LocalDate) {
        Timber.d("onDateSelect) date: $date")
        updateState { copy(selectedDate = date) }
        Timber.d("onDateSelect) updated date: ${currentState.selectedDate}")
    }

    private fun updateUserCrops() {
        Timber.d("updateUserCrops) called")
        viewModelScopeEH.launch {
            getUserCrops().collect { userCrops ->
                Timber.d("getUserCrops) userCrops: $userCrops")
                userCrops.map { CropModelMapper.toRight(it) }.let {
                    updateState {
                        copy(userCrops = it)
                    }
                    updateCrop(currentState.userCrops.firstOrNull())
                }
            }
        }
    }

    private fun updateCrop(newCrop: CropModel?) {
        Timber.d("updateCrop) new crop: $newCrop")
        if (currentState.crop == newCrop) return

        updateState { copy(crop = newCrop) }
        updateFarmWorks()
        updateCropSchedules()
        updateDiaries()
    }

    private fun updateYear(newYear: Int) {
        Timber.d("updateYear) newYear: $newYear")
        if (currentState.year == newYear) return

        updateState { copy(year = newYear) }
        updateHolidays()
        updateAllSchedules()
        updateCropSchedules()
        updateDiaries()
    }

    private fun updateMonth(newMonth: Int) {
        Timber.d("updateMonth) newMonth: $newMonth")
        if (currentState.month == newMonth) return

        updateState { copy(month = newMonth) }

        updateFarmWorks()
        updateHolidays()

        updateAllSchedules()
        updateCropSchedules()
        updateDiaries()
    }

    private fun updateFarmWorks() {
        Timber.d("updateFarmWorks) crop: ${currentState.crop}")
        if (currentState.crop == null) {
            updateState { copy(farmWorks = emptyList()) }
            return
        }

        viewModelScopeEH.launch {
            getFarmWorks(
                GetFarmWorksUseCase.Params(
                    crop = CropModelTypeMapper.toLeft(currentState.crop!!.type),
                    month = currentState.month
                )
            ).let { farmWorks ->
                Timber.d("getFarmworks) farmWorks: ${farmWorks.map { it.farmWork }}")
                updateState { copy(farmWorks = farmWorks.map { FarmWorkModelMapper.convert(it) }) }
            }
        }
    }

    private fun updateHolidays() {
        Timber.d("updateHolidays) called")
        viewModelScopeEH.launch {
            getHolidays(
                GetHolidaysUseCase.Params(
                    year = currentState.year,
                    month = currentState.month
                )
            ).let { holidays ->
                Timber.d("getHolidays) holidays: ${holidays.map { it.name }}")
                updateState {
                    copy(holidays = holidays.map { HolidayModelMapper.toRight(it) })
                }
            }
        }
    }

    private fun updateAllSchedules() {
        Timber.d("updateAllSchedules) called")
        viewModelScopeEH.launch {
            getSchedules(
                GetSchedulesUseCase.Params.Monthly(
                    category = ScheduleModelTypeMapper.toLeft(ScheduleModelType.All),
                    year = currentState.year,
                    month = currentState.month
                )
            ).collect { allSchedules ->
                Timber.d("getSchedules) allSchedules: ${allSchedules.map { it.title }}")
                updateState {
                    copy(allSchedules = allSchedules.map { ScheduleModelMapper.toRight(it) })
                }
            }
        }
    }

    private fun updateCropSchedules() {
        Timber.d("updateCropSchedules) called")
        if (currentState.crop == null) {
            updateState { copy(cropSchedules = emptyList()) }
            return
        }

        viewModelScopeEH.launch {
            getSchedules(
                GetSchedulesUseCase.Params.Monthly(
                    category = ScheduleModelTypeMapper.toLeft(
                        ScheduleModelType.Crop(currentState.crop!!)
                    ),
                    year = currentState.year,
                    month = currentState.month
                )
            ).collect { cropSchedules ->
                Timber.d("getSchedules) cropSchedules: ${cropSchedules.map { it.title }}")
                updateState {
                    copy(cropSchedules = cropSchedules.map { ScheduleModelMapper.toRight(it) })
                }
            }
        }
    }

    private fun updateDiaries() {
        Timber.d("updateDiaries) called")
        if (currentState.crop == null) {
            updateState { copy(diaries = emptyList()) }
            return
        }

        viewModelScopeEH.launch {
            getDiaries(
                GetDiariesUseCase.Params(
                    crop = CropModelMapper.toLeft(currentState.crop!!),
                    year = currentState.year,
                    month = currentState.month
                )
            ).collect { diaries ->
                Timber.d("getDiaries) diaries: ${diaries.map { it.date }}")
                updateState {
                    copy(diaries = diaries.map { DiaryModelMapper.toRight(it) })
                }
            }
        }
    }
}