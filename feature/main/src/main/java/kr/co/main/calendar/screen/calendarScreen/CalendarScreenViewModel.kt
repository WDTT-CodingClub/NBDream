package kr.co.main.calendar.screen.calendarScreen

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.GetUserCropsUseCase
import kr.co.main.R
import kr.co.main.mapper.calendar.CropModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

internal interface CalendarScreenEvent {
    fun onSelectYear(year: Int)
    fun onSelectMonth(month: Int)
    fun onSelectCrop(crop: CropModel)
}

@HiltViewModel
internal class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserCrop: GetUserCropsUseCase
) : BaseViewModel<CalendarScreenViewModel.CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    private val _userCrops = MutableStateFlow<List<CropModel>>(emptyList())

    data class CalendarScreenState(
        val userCrops: List<CropModel> = emptyList(),
        val calendarCrop: CropModel? = if (userCrops.isNotEmpty()) userCrops.first() else null,

        val calendarYear: Int = LocalDate.now().year,
        val calendarMonth: Int = LocalDate.now().monthValue
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
            return null
        }
    }

    enum class CalendarTabType(
        val pagerIndex: Int,
        @StringRes val titleId: Int
    ) {
        SCHEDULE(0, R.string.feature_main_calendar_tab_title_schedule),
        DIARY(1, R.string.feature_main_calendar_tab_title_diary);

        companion object{
            fun ofIndex(pagerIndex: Int) = when (pagerIndex) {
                SCHEDULE.pagerIndex -> SCHEDULE
                DIARY.pagerIndex -> DIARY
                else -> throw IllegalStateException("pagerState.currentPage is not valid")
            }
        }
    }

    val event: CalendarScreenEvent = this@CalendarScreenViewModel

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        savedState?.let {
            // TODO("deserialize")
            CalendarScreenState()
        } ?: CalendarScreenState()

    init {
        viewModelScopeEH.launch {
            getUserCrop().collect { userCrops ->
                updateState {
                    copy(userCrops = userCrops.map {
                        CropModelMapper.toRight(it)
                    })
                }
                Timber.d("userCrops: $userCrops")
            }
        }
        state.select { it.userCrops }.bindState(_userCrops)
        viewModelScopeEH.launch {
            _userCrops.collect { userCrops ->
                if (userCrops.isNotEmpty()) {
                    updateState { copy(calendarCrop = userCrops.first()) }
                }
            }
        }
    }

    override fun onSelectYear(year: Int) {
        updateState { copy(calendarYear = year) }
    }

    override fun onSelectMonth(month: Int) {
        updateState { copy(calendarMonth = month) }
    }

    override fun onSelectCrop(crop: CropModel) {
        updateState { copy(calendarCrop = crop) }
    }
}