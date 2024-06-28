package kr.co.main.calendar.screen.calendarScreen

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.GetDiariesUseCase
import kr.co.domain.usecase.calendar.GetFarmWorksUseCase
import kr.co.domain.usecase.calendar.GetHolidaysUseCase
import kr.co.domain.usecase.calendar.GetSchedulesUseCase
import kr.co.domain.usecase.calendar.GetUserCropsUseCase
import kr.co.domain.usecase.user.FetchUserUseCase
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
    private val fetchUserUseCase: FetchUserUseCase,
    private val getUserCrops: GetUserCropsUseCase,
    private val getFarmWorks: GetFarmWorksUseCase,
    private val getHolidays: GetHolidaysUseCase,
    private val getSchedules: GetSchedulesUseCase,
    private val getDiaries: GetDiariesUseCase,
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
        val diaries: List<DiaryModel> = emptyList(),
    ) : State {
    }

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        savedState?.let {
            // TODO("deserialize")
            CalendarScreenState()
        } ?: CalendarScreenState()

    init {
        viewModelScopeEH.launch { updateHolidays() }
        viewModelScopeEH.launch { updateAllSchedules() }
        viewModelScopeEH.launch { updateUserCrops() }

        viewModelScopeEH.launch {
            state.select(CalendarScreenState::crop).filterNotNull()
                .combine(state.select(CalendarScreenState::month)) { crop, month ->
                    GetFarmWorksUseCase.Params(
                        crop = crop.type.let(CropModelTypeMapper::toLeft),
                        month = month
                    )
                }.collectLatest { params ->
                    runCatching {
                        getFarmWorks(params)
                    }.onSuccess {
                        updateState {
                            copy(farmWorks = it.map(FarmWorkModelMapper::convert))
                        }
                    }.onFailure {
                        updateState {
                            copy(farmWorks = emptyList())
                        }
                    }
                }
        }

        viewModelScopeEH.launch {
            error.collect {
                Timber.e("${it.throwable?.message}\n${it.customError}\n${it.throwable?.cause}")
            }
        }

        viewModelScopeEH.launch {
            state.collectLatest {
                Timber.i("state : $it")
            }
        }
    }

    override fun onYearSelect(year: Int) {
        Timber.d("onYearSelect) year: $year")
        viewModelScopeEH.launch { updateYear(year) }
    }

    override fun onMonthSelect(month: Int) = updateState {
        copy(month = month)
    }

    override fun onCropSelect(crop: CropModel) = updateState {
        copy(crop = crop)
    }

    override fun onDateSelect(date: LocalDate) {
        Timber.d("onDateSelect) date: $date")
        updateState { copy(selectedDate = date) }
        Timber.d("onDateSelect) updated date: ${currentState.selectedDate}")
    }

    private suspend fun updateUserCrops() {
        getUserCrops.invoke()
            .collectLatest { userCrops ->
                userCrops.map { CropModelMapper.toRight(it) }.let {
                    updateState {
                        copy(
                            crop = it.first(),
                            userCrops = it
                        )
                    }
                }
            }
    }

    private suspend fun updateCrop(newCrop: CropModel) {
        Timber.d("updateCrop) newCrop: $newCrop")
        if (currentState.crop == newCrop) return
        Timber.d("updateCrop) currentState.crop: ${currentState.crop}")


        updateState { copy(crop = newCrop) }

//        updateFarmWorks()
//        updateCropSchedules()
//        updateDiaries()
    }

    private suspend fun updateYear(newYear: Int) {
        Timber.d("updateYear) newYear: $newYear")
        if (currentState.year == newYear) return

        updateState { copy(year = newYear) }

//        updateHolidays()
//        updateAllSchedules()
//        updateCropSchedules()
//        updateDiaries()
    }

    private suspend fun updateMonth(newMonth: Int) {
        Timber.d("updateMonth) newMonth: $newMonth")
        if (currentState.month == newMonth) return

        updateState { copy(month = newMonth) }

//        updateFarmWorks()
//        updateHolidays()
//        updateAllSchedules()
//        updateCropSchedules()
//        updateDiaries()
    }

    private suspend fun updateFarmWorks() {
        Timber.d("updateFarmWorks) called, crop: ${currentState.crop?.type?.name}")
        getFarmWorks(
            GetFarmWorksUseCase.Params(
                crop = currentState.crop?.type?.let { CropModelTypeMapper.toLeft(it) },
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