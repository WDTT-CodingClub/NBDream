package kr.co.main.calendar.screen.addScheduleScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.CreateDiaryUseCase
import kr.co.domain.usecase.calendar.CreateScheduleUseCase
import kr.co.domain.usecase.calendar.DeleteDiaryUseCase
import kr.co.domain.usecase.calendar.DeleteScheduleUseCase
import kr.co.domain.usecase.calendar.GetScheduleDetailUseCase
import kr.co.domain.usecase.calendar.UpdateDiaryUseCase
import kr.co.domain.usecase.calendar.UpdateScheduleUseCase
import kr.co.main.mapper.calendar.HolidayModelMapper
import kr.co.main.mapper.calendar.ScheduleModelTypeMapper
import kr.co.main.mapper.calendar.WorkDescriptionModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.main.model.calendar.type.ScreenModeType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface AddScheduleScreenEvent {
    fun onPostClick()
    fun onEditClick()
    fun onDeleteClick()

    fun onTypeInput(type: ScheduleModelType)
    fun onTitleInput(title: String)
    fun onStartDateInput(startDate: LocalDate)
    fun onEndDateInput(endDate: LocalDate)
    fun onMemoInput(memo: String)
}

@HiltViewModel
internal class AddScheduleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSchedule: GetScheduleDetailUseCase,
    private val createSchedule: CreateScheduleUseCase,
    private val updateSchedule: UpdateScheduleUseCase,
    private val deleteSchedule: DeleteScheduleUseCase
) : BaseViewModel<AddScheduleViewModel.AddScheduleScreenState>(savedStateHandle),
    AddScheduleScreenEvent {
    private val _scheduleId = MutableStateFlow<Long?>(null)
    private val _title = MutableStateFlow("")

    val event: AddScheduleScreenEvent = this@AddScheduleViewModel

    data class AddScheduleScreenState(
        val screenMode: ScreenModeType = ScreenModeType.POST_MODE,
        val calendarCrop: CropModel? = null,

        val enableAction: Boolean = false,

        val scheduleId: Long? = null,
        val scheduleType: ScheduleModelType = ScheduleModelType.All,
        val title: String = "",
        val startDate: LocalDate = LocalDate.now(),
        val endDate: LocalDate = LocalDate.now(),
        val memo: String = ""
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
        with(savedStateHandle) {
            get<String>(CalendarNavGraph.ARG_CROP_NAME_ID)?.toIntOrNull()?.let { cropNameId ->
                updateState {
                    copy(calendarCrop = CropModel.create(CropModelType.ofValue(cropNameId)))
                }
            }
            get<Int>(CalendarNavGraph.ARG_SCREEN_MODE_ID)?.let { screenModeId ->
                updateState {
                    copy(screenMode = ScreenModeType.ofValue(screenModeId))
                }
            } ?: throw IllegalArgumentException("screen mode id is null")
            get<String>(CalendarNavGraph.ARG_SCHEDULE_ID)?.toLongOrNull()?.let { scheduleId ->
                updateState {
                    copy(scheduleId = scheduleId)
                }
            }
        }

        with(state) {
            select { it.scheduleId }.bindState(_scheduleId)
            select { it.title }.bindState(_title)
        }

        viewModelScopeEH.launch {
            _scheduleId.collect { scheduleId ->
                scheduleId?.let { id ->
                    getSchedule(GetScheduleDetailUseCase.Params(id)).let {
                        updateState {
                            copy(
                                scheduleType = ScheduleModelTypeMapper.toRight(it.type),
                                title = it.title,
                                startDate = it.startDate,
                                endDate = it.endDate,
                                memo = it.memo
                            )
                        }
                    }
                }
            }
        }
        viewModelScopeEH.launch {
            _title.collect { title ->
                updateState { copy(enableAction = title.isNotEmpty()) }
            }
        }
    }

    override fun onPostClick() {
        if (!currentState.enableAction) return

        if(currentState.screenMode != ScreenModeType.POST_MODE)
            throw IllegalStateException ("screen mode is not post mode")

        viewModelScopeEH.launch {
            viewModelScopeEH.launch {
                createSchedule(
                    CreateScheduleUseCase.Params(
                        category = ScheduleModelTypeMapper.toLeft(currentState.scheduleType),
                        title = currentState.title,
                        startDate = currentState.startDate.toString(),
                        endDate = currentState.endDate.toString(),
                        memo = currentState.memo
                    )
                )
            }
        }
    }

    override fun onEditClick() {
        if (!currentState.enableAction) return

        if(currentState.screenMode != ScreenModeType.EDIT_MODE)
            throw IllegalStateException ("screen mode is not edit mode")
        checkNotNull(currentState.scheduleId)

        viewModelScopeEH.launch {
            updateSchedule(
                UpdateScheduleUseCase.Params(
                    id = currentState.scheduleId!!,
                    category = ScheduleModelTypeMapper.toLeft(currentState.scheduleType),
                    title = currentState.title,
                    startDate = currentState.startDate.toString(),
                    endDate = currentState.endDate.toString(),
                    memo = currentState.memo
                )
            )
        }
    }

    override fun onDeleteClick() {
        if (!currentState.enableAction) return

        if(currentState.screenMode != ScreenModeType.EDIT_MODE)
            throw IllegalStateException ("screen mode is not edit mode")
        checkNotNull(currentState.scheduleId)

        viewModelScopeEH.launch {
            deleteSchedule(DeleteScheduleUseCase.Params(currentState.scheduleId!!))
        }
    }

    override fun onTypeInput(type: ScheduleModelType) {
        updateState { copy(scheduleType = type)}
    }

    override fun onTitleInput(title: String) {
        updateState { copy(title = title) }
    }

    override fun onStartDateInput(startDate: LocalDate) {
        updateState { copy(startDate = startDate) }
    }

    override fun onEndDateInput(endDate: LocalDate) {
        updateState { copy(endDate = endDate) }
    }

    override fun onMemoInput(memo: String) {
        updateState { copy(memo = memo) }
    }
}