package kr.co.main.calendar.screen.addDiaryScreen

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.common.util.FileUtil
import kr.co.domain.usecase.calendar.CreateDiaryUseCase
import kr.co.domain.usecase.calendar.DeleteDiaryUseCase
import kr.co.domain.usecase.calendar.GetDiaryDetailUseCase
import kr.co.domain.usecase.calendar.GetHolidaysUseCase
import kr.co.domain.usecase.calendar.UpdateDiaryUseCase
import kr.co.domain.usecase.image.DeleteImageUseCase
import kr.co.domain.usecase.image.UploadImageUseCase
import kr.co.main.mapper.calendar.HolidayModelMapper
import kr.co.main.mapper.calendar.WorkDescriptionModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.filterAndSortHolidays
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScreenModeType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface AddDiaryScreenEvent {
    fun onPostClick()
    fun onEditClick()
    fun onDeleteClick()

    fun onDateSelect(date: LocalDate)
    fun onAddImage(imageUris: List<Uri>)
    fun onDeleteImage(imageUrl: String)
    fun onMemoInput(memo: String)
    fun onAddWorkDescription(workDescription: DiaryModel.WorkDescriptionModel)

    fun onDeleteWorkDescription(index: Int)
    fun onWorkLaborerInput(workLaborer: Int)
    fun onWorkHourInput(workHour: Int)
    fun onWorkAreaInput(workArea: Int)
}

@HiltViewModel
internal class AddDiaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDiary: GetDiaryDetailUseCase,
    private val createDiary: CreateDiaryUseCase,
    private val updateDiary: UpdateDiaryUseCase,
    private val deleteDiary: DeleteDiaryUseCase,

    private val uploadImage: UploadImageUseCase,
    private val deleteImage: DeleteImageUseCase,
    private val getHolidays: GetHolidaysUseCase,

    ) : BaseViewModel<AddDiaryViewModel.AddDiaryScreenState>(savedStateHandle),
    AddDiaryScreenEvent {

    val event: AddDiaryScreenEvent = this@AddDiaryViewModel

    private val _diaryId = MutableStateFlow<Int?>(null)
    private val _date = MutableStateFlow<LocalDate>(LocalDate.now())
    private val _memo = MutableStateFlow("")
    private val _showPreviousScreen = MutableSharedFlow<Unit>()
    val showPreviousScreen = _showPreviousScreen.asSharedFlow()

    data class AddDiaryScreenState(
        val screenMode: ScreenModeType = ScreenModeType.POST_MODE,
        val calendarCrop: CropModel? = null,

        val enableAction: Boolean = false,

        val diaryId: Int? = null,

        val date: LocalDate = LocalDate.now(),
        val isDateValid: Boolean = true,
        val dateGuid: String? = null,

        val images: List<String> = emptyList(),

        val memo: String = "",
        val isMemoValid: Boolean = true,
        val memoGuid: String? = null,

        val workDescriptions: List<DiaryModel.WorkDescriptionModel> = emptyList(),
        val workLaborer: Int = 0,
        val workHour: Int = 0,
        val workArea: Int = 0,

        val weatherInfo: String = "", //TODO 일일 날씨 정보 조회
        val holidays: List<HolidayModel> = emptyList()
    ) : State {
        val isFieldValid: Boolean
            get() = isMemoValid && isDateValid
    }

    override fun createInitialState(savedState: Parcelable?): AddDiaryScreenState =
        savedState?.let {
            // TODO("deserialize")
            AddDiaryScreenState()
        } ?: AddDiaryScreenState()

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
            }
            get<String>(CalendarNavGraph.ARG_DIARY_ID)?.toIntOrNull()?.let { diaryId ->
                updateState {
                    copy(diaryId = diaryId)
                }
            }
        }

        with(state) {
            select { it.diaryId }.bindState(_diaryId)
            select { it.date }.bindState(_date)
            select { it.memo }.bindState(_memo)
        }

        viewModelScopeEH.launch {
            _diaryId.collect { diaryId ->
                diaryId?.let {
                    getDiary(GetDiaryDetailUseCase.Params(it)).let { diaryEntity ->
                        updateState {
                            copy(
                                date = diaryEntity.date,
                                images = diaryEntity.images,
                                memo = diaryEntity.memo,
                                workDescriptions = diaryEntity.workDescriptions.map {
                                    WorkDescriptionModelMapper.toRight(it)
                                },
                                workLaborer = diaryEntity.workLaborer,
                                workHour = diaryEntity.workHours,
                                workArea = diaryEntity.workArea
                            )
                        }
                    }
                }
            }
        }
        viewModelScopeEH.launch {
            _date.collect { date ->
                getHolidays(
                    GetHolidaysUseCase.Params(date.year, date.monthValue)
                ).let { holidays ->
                    updateState {
                        copy(
                            holidays = holidays.map {
                                HolidayModelMapper.toRight(it)
                            }.let {
                                filterAndSortHolidays(it, date)
                            }
                        )
                    }
                }
            }
        }
        viewModelScopeEH.launch {
            _memo.collect { memo ->
                updateState { copy(enableAction = memo.isNotEmpty()) }
            }
        }
    }

    private fun checkedValid() = updateState {
        val isMemoValid = currentState.memo.isNotEmpty()
        val isDateValid = currentState.date <= LocalDate.now()

        copy(
            isDateValid = isDateValid,
            dateGuid = if (isDateValid) {
                null
            } else {
                "날짜를 확인해 주세요"
            },


            isMemoValid = isMemoValid,
            memoGuid = if (isMemoValid) {
                null
            } else {
                "메모를 입력해 주세요"
            }
        )
    }

    override fun onPostClick() {

        if (currentState.screenMode != ScreenModeType.POST_MODE)
            throw IllegalStateException("screen mode is not post mode")
        checkNotNull(currentState.calendarCrop)

        checkedValid()

        if (currentState.isFieldValid) {
            viewModelScopeEH.launch {
                createDiary(
                    CreateDiaryUseCase.Params(
                        crop = currentState.calendarCrop!!.type.name,
                        date = currentState.date,
                        memo = currentState.memo,
                        workDescriptions = currentState.workDescriptions.map {
                            WorkDescriptionModelMapper.toLeft(it)
                        },
                        imageUrls = currentState.images,
                        workLaborer = currentState.workLaborer,
                        workHours = currentState.workHour,
                        workArea = currentState.workArea,
                        weatherForecast = "",
                        holidayList = currentState.holidays.map {
                            HolidayModelMapper.toLeft(it)
                        }
                    )
                )
            }.invokeOnCompletion {
                if (it == null)
                    viewModelScopeEH.launch {
                        _showPreviousScreen.emit(Unit)
                    }
            }
        }
    }

    override fun onEditClick() {
        if (!currentState.enableAction) return

        if (currentState.screenMode != ScreenModeType.EDIT_MODE)
            throw IllegalStateException("screen mode is not edit mode")
        checkNotNull(currentState.calendarCrop)
        checkNotNull(currentState.diaryId)

        viewModelScopeEH.launch {
            updateDiary(
                UpdateDiaryUseCase.Params(
                    id = currentState.diaryId!!,
                    crop = currentState.calendarCrop!!.type.name,
                    date = currentState.date,
                    memo = currentState.memo,
                    workDescriptions = currentState.workDescriptions.map {
                        WorkDescriptionModelMapper.toLeft(it)
                    },
                    workLaborer = currentState.workLaborer,
                    workHours = currentState.workHour,
                    workArea = currentState.workArea,
                    weatherForecast = currentState.weatherInfo,
                    holidayList = currentState.holidays.map {
                        HolidayModelMapper.toLeft(it)
                    }
                )
            )
        }.invokeOnCompletion {
            if (it == null)
                viewModelScopeEH.launch {
                    _showPreviousScreen.emit(Unit)
                }
        }
    }

    override fun onDeleteClick() {
        if (!currentState.enableAction) return

        if (currentState.screenMode != ScreenModeType.EDIT_MODE)
            throw IllegalStateException("screen mode is not edit mode")
        checkNotNull(currentState.diaryId)

        viewModelScopeEH.launch {
            deleteDiary(DeleteDiaryUseCase.Params(currentState.diaryId!!))
        }
    }

    override fun onDateSelect(date: LocalDate) {
        updateState { copy(date = date) }
    }

    override fun onAddImage(imageUris: List<Uri>) {
        viewModelScopeEH.launch {
            for (uri in imageUris) {
                uploadImage(
                    UploadImageUseCase.Params(
                        domain = UploadImageUseCase.DOMAIN_DIARY,
                        file = FileUtil.getFileFromUri(uri)
                            ?: throw IllegalArgumentException("image file is null")
                    )
                ).let { newImage ->
                    updateState { copy(images = images + newImage) }
                }
            }
        }
    }

    override fun onDeleteImage(imageUrl: String) {
        viewModelScopeEH.launch {
            deleteImage(imageUrl)
        }
        updateState { copy(images = images.toMutableList().apply { remove(imageUrl) }) }
    }

    override fun onMemoInput(memo: String) {
        updateState { copy(memo = memo) }
    }

    override fun onAddWorkDescription(workDescription: DiaryModel.WorkDescriptionModel) {
        updateState {
            copy(
                workDescriptions =
                workDescriptions + workDescription
            )
        }
    }

    override fun onDeleteWorkDescription(index: Int) {
        updateState {
            copy(
                workDescriptions =
                workDescriptions.toMutableList().apply { removeAt(index) }
            )
        }
    }

    override fun onWorkLaborerInput(workLaborer: Int) {
        updateState { copy(workLaborer = workLaborer) }
    }

    override fun onWorkHourInput(workHour: Int) {
        updateState { copy(workHour = workHour) }
    }

    override fun onWorkAreaInput(workArea: Int) {
        updateState { copy(workArea = workArea) }
    }
}