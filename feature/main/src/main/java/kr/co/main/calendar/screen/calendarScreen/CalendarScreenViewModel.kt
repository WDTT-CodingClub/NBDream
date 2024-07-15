package kr.co.main.calendar.screen.calendarScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.DeleteDiaryUseCase
import kr.co.domain.usecase.calendar.DeleteScheduleUseCase
import kr.co.domain.usecase.calendar.GetDiariesUseCase
import kr.co.domain.usecase.calendar.GetFarmWorksUseCase
import kr.co.domain.usecase.calendar.GetHolidaysUseCase
import kr.co.domain.usecase.calendar.GetLocalUserCropsUseCase
import kr.co.domain.usecase.calendar.GetSchedulesUseCase
import kr.co.main.calendar.common.dialog.CalendarDialogState
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

    fun onDeleteScheduleSelect(scheduleId: Long)
    fun onDeleteDiarySelect(diaryId: Long)

    fun showDeleteScheduleDialog()
    fun showDeleteDiaryDialog()
}

@HiltViewModel
internal class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLocalUserCrops: GetLocalUserCropsUseCase,
    private val getFarmWorks: GetFarmWorksUseCase,
    private val getHolidays: GetHolidaysUseCase,
    private val getSchedules: GetSchedulesUseCase,
    private val getDiaries: GetDiariesUseCase,
    private val deleteSchedule: DeleteScheduleUseCase,
    private val deleteDiary: DeleteDiaryUseCase
) : BaseViewModel<CalendarScreenViewModel.CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    private val _crop = MutableStateFlow<CropModel?>(null)
    private val _year = MutableStateFlow(LocalDate.now().year)
    private val _month = MutableStateFlow(LocalDate.now().monthValue)

    val event: CalendarScreenEvent = this@CalendarScreenViewModel

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
        val diaries: List<DiaryModel> = emptyList(),

        val deleteScheduleId: Long? = null,
        val deleteDiaryId: Long? = null,

        val showDialog: Boolean = false,
        val dialogState: CalendarDialogState? = null
    ) : State

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        CalendarScreenState()

    init {
        Timber.d("init) called")
        // collect error
        viewModelScopeEH.launch {
            error.collect {
                Timber.e("error: ${it.throwable?.message}\n${it.customError}\n${it.throwable?.cause}")
            }
        }

        // bind view model state to state flow
        with(state) {
            select { it.crop }.bindState(_crop)
            select { it.year }.bindState(_year)
            select { it.month }.bindState(_month)
        }
        // collect state flow
        viewModelScopeEH.launch {
            _crop.collect {
                updateFarmWorks()
                updateCropSchedules()
                updateDiaries()
            }
        }
        viewModelScopeEH.launch {
            _year.collect {
                updateHolidays()
                updateAllSchedules()
                updateCropSchedules()
                updateDiaries()
            }
        }
        viewModelScopeEH.launch {
            _month.collect {
                updateFarmWorks()
                updateHolidays()

                updateAllSchedules()
                updateCropSchedules()
                updateDiaries()
            }
        }
        // collect room flow
        viewModelScopeEH.launch {
            getLocalUserCrops().collect { userCrops ->
                Timber.d("getUserCrops) userCrops: $userCrops")
                userCrops.map { CropModelMapper.toRight(it) }.let {
                    updateState {
                        copy(userCrops = it)
                    }
                    updateState {
                        copy(crop = it.firstOrNull())
                    }
                }
            }
        }
    }

    fun reinitialize() {
        Timber.d("reinitialize) called")
        updateAllSchedules()
        updateCropSchedules()
        updateDiaries()
    }

    override fun onYearSelect(year: Int) {
        Timber.d("onYearSelect) year: $year")
        updateState { copy(year = year) }
    }

    override fun onMonthSelect(month: Int) {
        Timber.d("onMonthSelect) month: $month")
        updateState { copy(month = month) }
    }

    override fun onCropSelect(crop: CropModel) {
        Timber.d("onCropSelect) crop: $crop")
        updateState { copy(crop = crop) }
    }

    override fun onDateSelect(date: LocalDate) {
        Timber.d("onDateSelect) date: $date")
        updateState { copy(selectedDate = date) }
        Timber.d("onDateSelect) updated date: ${currentState.selectedDate}")
    }

    override fun onDeleteScheduleSelect(scheduleId: Long) {
        updateState { copy(deleteScheduleId = scheduleId) }
    }

    override fun onDeleteDiarySelect(diaryId: Long) {
        updateState { copy(deleteDiaryId = diaryId) }
    }

    override fun showDeleteScheduleDialog() {
        updateState {
            copy(
                showDialog = true,
                dialogState = CalendarDialogState.DeleteScheduleDialogState(
                    _onConfirm = this@CalendarScreenViewModel::onDeleteSchedule,
                    _onDismissRequest = this@CalendarScreenViewModel::onDismissRequest
                )
            )
        }
    }

    override fun showDeleteDiaryDialog() {
        updateState {
            copy(
                showDialog = true,
                dialogState = CalendarDialogState.DeleteDiaryDialogState(
                    _onConfirm = this@CalendarScreenViewModel::onDeleteDiary,
                    _onDismissRequest = this@CalendarScreenViewModel::onDismissRequest
                )
            )
        }
    }

    private fun onDeleteSchedule() {
        checkNotNull(currentState.deleteScheduleId)
        viewModelScopeEH.launch {
            deleteSchedule(DeleteScheduleUseCase.Params(currentState.deleteScheduleId!!))
        }.invokeOnCompletion {
            reinitialize()
        }
    }

    private fun onDeleteDiary() {
        checkNotNull(currentState.deleteDiaryId)
        viewModelScopeEH.launch {
            deleteDiary(DeleteDiaryUseCase.Params(currentState.deleteDiaryId!!))
        }.invokeOnCompletion {
            reinitialize()
        }
    }

    private fun onDismissRequest() {
        updateState { copy(showDialog = false) }
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
            ).cancellable().collect { allSchedules ->
                Timber.d("getSchedules) allSchedules: ${allSchedules.map { it.title }}")
                updateState {
                    copy(allSchedules = allSchedules.map { ScheduleModelMapper.toRight(it) })
                }
                this.coroutineContext.job.cancel()
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
            ).cancellable().collect { cropSchedules ->
                Timber.d("getSchedules) cropSchedules: ${cropSchedules.map { it.title }}")
                updateState {
                    copy(cropSchedules = cropSchedules.map { ScheduleModelMapper.toRight(it) })
                }
                this.coroutineContext.job.cancel()
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
            ).cancellable().collect { diaries ->
                Timber.d("getDiaries) diaries: ${diaries.map { it.date }}")
                updateState {
                    copy(diaries = diaries.map { DiaryModelMapper.toRight(it) })
                }
                this.coroutineContext.job.cancel()
            }
        }
    }
}