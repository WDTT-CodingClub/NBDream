package kr.co.main.calendar.screen.calendarScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
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
import kr.co.main.model.calendar.type.CropModelType
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
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
            return null
        }
    }

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        savedState?.let {
            // TODO("deserialize")
            CalendarScreenState()
        } ?: CalendarScreenState()

    init {
        Timber.d("init) called")
        viewModelScopeEH.launch { updateUserCrops() }
        viewModelScopeEH.launch { updateHolidays() }
        viewModelScopeEH.launch { updateAllSchedules() }
    }

    override fun onYearSelect(year: Int) {
        Timber.d("onYearSelect) year: $year")
        viewModelScopeEH.launch { updateYear(year) }
    }

    override fun onMonthSelect(month: Int) {
        Timber.d("onMonthSelect) month: $month")
        viewModelScopeEH.launch { updateMonth(month) }
    }

    override fun onCropSelect(crop: CropModel) {
        Timber.d("onCropSelect) crop: ${crop.type.name}")
        viewModelScopeEH.launch { updateCrop(crop) }
    }

    override fun onDateSelect(date: LocalDate) {
        Timber.d("onDateSelect) date: $date")
        updateState { copy(selectedDate = date) }
    }

    private suspend fun updateUserCrops() {
        Timber.d("updateUserCrops) called")
//        viewModelScopeEH.launch {
//            getUserCrops().collect { userCrops ->
//                userCrops.map { CropModelMapper.toRight(it) }.let {
//                    updateState {
//                        copy(userCrops = it)
//                    }
//                }
//            }
//        }.join()
//        updateCrop(currentState.userCrops.firstOrNull())

        val tmpUserCrops = listOf(
            CropModel.create(CropModelType.POTATO),
            CropModel.create(CropModelType.SWEET_POTATO),
            CropModel.create(CropModelType.APPLE),
            CropModel.create(CropModelType.GARLIC),
        )
        updateState { copy(userCrops = tmpUserCrops) }
        updateCrop(tmpUserCrops.firstOrNull())
    }

    private suspend fun updateCrop(newCrop: CropModel?) {
        Timber.d("updateCrop) newCrop: $newCrop")
        if (currentState.crop == newCrop) return

        viewModelScopeEH.launch {
            updateState { copy(crop = newCrop) }
        }.join()
        updateFarmWorks()
        updateCropSchedules()
        updateDiaries()
    }

    private suspend fun updateYear(newYear: Int) {
        Timber.d("updateYear) newYear: $newYear")
        if (currentState.year == newYear) return

        viewModelScopeEH.launch {
            updateState { copy(year = newYear) }
        }.join()
        updateHolidays()
        updateAllSchedules()
        updateCropSchedules()
        updateDiaries()
    }

    private suspend fun updateMonth(newMonth: Int) {
        Timber.d("updateMonth) newMonth: $newMonth")
        if (currentState.month == newMonth) return

        viewModelScopeEH.launch {
            updateState { copy(month = newMonth) }
        }.join()
        updateFarmWorks()
        updateHolidays()
        updateAllSchedules()
        updateCropSchedules()
        updateDiaries()
    }

    private suspend fun updateFarmWorks() {
        Timber.d("updateFarmWorks) called, crop: ${currentState.crop?.type?.name}")
        if (currentState.crop == null) {
            updateState { copy(farmWorks = emptyList()) }
            return
        }

        getFarmWorks(
            GetFarmWorksUseCase.Params(
                crop = CropModelTypeMapper.toLeft(currentState.crop!!.type),
                month = currentState.month
            )
        ).let { farmWorks ->
            Timber.d("farmWorks: ${farmWorks.map { it.farmWork }}")
            updateState { copy(farmWorks = farmWorks.map { FarmWorkModelMapper.convert(it) }) }
        }
    }

    private suspend fun updateHolidays() {
        Timber.d("updateHolidays) called")
        getHolidays(
            GetHolidaysUseCase.Params(
                year = currentState.year,
                month = currentState.month
            )
        ).let { holidays ->
            updateState {
                copy(holidays = holidays.map { HolidayModelMapper.toRight(it) })
            }
        }
    }

    private suspend fun updateAllSchedules() {
        Timber.d("updateAllSchedules) called")
        getSchedules(
            GetSchedulesUseCase.Params.Monthly(
                category = ScheduleModelTypeMapper.toLeft(ScheduleModelType.All),
                year = currentState.year,
                month = currentState.month
            )
        ).collect { allSchedules ->
            updateState {
                copy(allSchedules = allSchedules.map { ScheduleModelMapper.toRight(it) })
            }
        }
    }

    private suspend fun updateCropSchedules() {
        Timber.d("updateCropSchedules) called")
        if (currentState.crop == null) {
            updateState { copy(cropSchedules = emptyList()) }
            return
        }

        getSchedules(
            GetSchedulesUseCase.Params.Monthly(
                category = ScheduleModelTypeMapper.toLeft(
                    ScheduleModelType.Crop(currentState.crop!!)
                ),
                year = currentState.year,
                month = currentState.month
            )
        ).collect { cropSchedules ->
            updateState {
                copy(cropSchedules = cropSchedules.map { ScheduleModelMapper.toRight(it) })
            }
        }
    }

    private suspend fun updateDiaries() {
        Timber.d("updateDiaries) called")
        if (currentState.crop == null) {
            updateState { copy(diaries = emptyList()) }
            return
        }

        getDiaries(
            GetDiariesUseCase.Params(
                crop = CropModelMapper.toLeft(currentState.crop!!),
                year = currentState.year,
                month = currentState.month
            )
        ).collect { diaries ->
            updateState {
                copy(diaries = diaries.map { DiaryModelMapper.toRight(it) })
            }
        }
    }
}