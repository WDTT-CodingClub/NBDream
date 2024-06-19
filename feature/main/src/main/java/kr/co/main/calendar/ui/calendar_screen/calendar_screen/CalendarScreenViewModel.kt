package kr.co.main.calendar.ui.calendar_screen.calendar_screen

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.GetFarmWorkUseCase
import kr.co.domain.usecase.calendar.GetHolidayUseCase
import kr.co.domain.usecase.calendar.GetUserCropUseCase
import kr.co.main.R
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.FarmWorkModel
import kr.co.main.calendar.model.HolidayModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.main.calendar.model.convert
import kr.co.main.calendar.ui.calendar_screen.calendar_screen.CalendarScreenViewModel.CalendarScreenState
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

internal interface CalendarScreenEvent {
    fun onSelectTab(tab: CalendarScreenState.CalendarTab)
    fun onSelectYear(year: Int)
    fun onSelectMonth(month: Int)
    fun onSelectCrop(crop: CropModel)
}

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserCrop: GetUserCropUseCase,
    private val getFarmWork: GetFarmWorkUseCase,
    private val getHoliday: GetHolidayUseCase
) : BaseViewModel<CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    private val TAG = this@CalendarScreenViewModel::class.java.simpleName

    private val _userCrops = MutableStateFlow<List<CropModel>>(emptyList())
    private val _calendarCrop = MutableStateFlow<CropModel?>(null)
    private val _calendarYear = MutableStateFlow(LocalDate.now().year)
    private val _calendarMonth = MutableStateFlow(LocalDate.now().monthValue)

    data class CalendarScreenState(
        val selectedTab: CalendarTab = CalendarTab.SCHEDULE,

        val calendarYear: Int = LocalDate.now().year,
        val calendarMonth: Int = LocalDate.now().monthValue,

        val userCrops: List<CropModel> = emptyList(),
        val calendarCrop: CropModel? = if (userCrops.isNotEmpty()) userCrops.first() else null,

        val farmWorks: List<FarmWorkModel> = emptyList(),
        val holidays: List<HolidayModel> = emptyList(),
        val schedules: List<ScheduleModel> = emptyList(),
        val diaries: List<DiaryModel> = emptyList()
    ) : State {
        enum class CalendarTab(@StringRes val titleId: Int) {
            SCHEDULE(R.string.feature_main_calendar_tab_title_schedule),
            DIARY(R.string.feature_main_calendar_tab_title_diary)
        }

        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
            return null
        }
    }

    val event = this@CalendarScreenViewModel

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        savedState?.let {
            // TODO("deserialize")
            CalendarScreenState()
        } ?: CalendarScreenState()

    init {
        viewModelScopeEH.launch {
            getUserCrop().collect { userCrops ->
                updateState {
                    currentState.copy(userCrops = userCrops.map { it.convert() })
                }
                Timber.tag(TAG).d("userCrops: $userCrops")
            }
        }
        state.select { it.userCrops }.bindState(_userCrops)
        viewModelScopeEH.launch {
            _userCrops.collect{ userCrops ->
                if(userCrops.isNotEmpty()) {
                    updateState {
                        currentState.copy(calendarCrop = userCrops.first())
                    }
                }
            }
        }

        state.select { it.calendarCrop }.bindState(_calendarCrop)
        state.select { it.calendarYear }.bindState(_calendarYear)
        state.select { it.calendarMonth }.bindState(_calendarMonth)
        combine(_calendarCrop, _calendarYear, _calendarMonth) { crop, year, month ->
            _calendarCrop.value?.let { crop ->
                getFarmWork(
                    GetFarmWorkUseCase.Params(
                        crop = crop.convert().name.koreanName,
                        year = year,
                        month = month
                    )
                ).collect { farmWorks ->
                    updateState {
                        currentState.copy(farmWorks = farmWorks.map { it.convert() })
                    }
                    Timber.tag(TAG).d("farmWorks: $farmWorks")
                }
            }
            getHoliday(
                GetHolidayUseCase.Params(
                    year = currentState.calendarYear,
                    month = currentState.calendarMonth
                )
            ).collect { holidays ->
                updateState {
                    currentState.copy(holidays = holidays.map { it.convert() })
                }
                Timber.tag(TAG).d("holidays: $holidays")
            }

        }.launchIn(viewModelScopeEH)
    }

    override fun onSelectTab(tab: CalendarScreenState.CalendarTab) {
        updateState { state.value.copy(selectedTab = tab) }
    }

    override fun onSelectYear(year: Int) {
        updateState { state.value.copy(calendarYear = year) }
    }

    override fun onSelectMonth(month: Int) {
        updateState { state.value.copy(calendarMonth = month) }
    }

    override fun onSelectCrop(crop: CropModel) {
        updateState { state.value.copy(calendarCrop = crop) }
    }
}