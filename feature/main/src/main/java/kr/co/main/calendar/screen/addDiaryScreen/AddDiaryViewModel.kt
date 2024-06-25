package kr.co.main.calendar.screen.addDiaryScreen

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.co.common.util.FileUtil
import kr.co.domain.usecase.calendar.CreateDiaryUseCase
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
import kr.co.main.model.calendar.type.WorkDescriptionModelType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface AddDiaryScreenEvent {
    fun onActionClick()

    fun onDateInput(date: LocalDate)
    fun onAddImage(imageUris: List<Uri>)

    fun onDeleteImage(imageUrl: String)
    fun onMemoInput(memo: String)
    fun onAddWorkDescription(
        workCategory: WorkDescriptionModelType,
        workDescription: String
    )

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
    private val uploadImage: UploadImageUseCase,
    private val deleteImage: DeleteImageUseCase,
    private val getHolidays: GetHolidaysUseCase
) : BaseViewModel<AddDiaryViewModel.AddDiaryScreenState>(savedStateHandle), AddDiaryScreenEvent {

    val event: AddDiaryScreenEvent = this@AddDiaryViewModel

    private val _diaryId = MutableStateFlow<Int?>(null)
    private val _date = MutableStateFlow<LocalDate>(LocalDate.now())
    private val _memo = MutableStateFlow("")

    data class AddDiaryScreenState(
        val screenMode: ScreenModeType = ScreenModeType.POST_MODE,
        val calendarCrop: CropModel? = null,

        val enableAction: Boolean = false,

        val diaryId: Int? = null,
        val date: LocalDate = LocalDate.now(),
        val images: List<String> = emptyList(),
        val memo: String = "",
        val workDescriptions: List<DiaryModel.WorkDescriptionModel> = emptyList(),
        val workLaborer: Int = 0,
        val workHour: Int = 0,
        val workArea: Int = 0,

        val weatherInfo: String = "", //TODO 일일 날씨 정보 조회
        val holidays: List<HolidayModel> = emptyList()
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
            return null
        }
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

    override fun onActionClick() {
        if (!currentState.enableAction) return
        when (currentState.screenMode) {
            ScreenModeType.POST_MODE -> onPostClick()
            ScreenModeType.EDIT_MODE -> onEditClick()
        }
    }

    private fun onPostClick() {
        checkNotNull(currentState.calendarCrop)
        viewModelScopeEH.launch {
            createDiary(
                CreateDiaryUseCase.Params(
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
        }
    }

    private fun onEditClick() {
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
        }
    }

    override fun onDateInput(date: LocalDate) {
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

    override fun onAddWorkDescription(
        workCategory: WorkDescriptionModelType,
        workDescription: String
    ) {
        updateState {
            copy(
                workDescriptions =
                workDescriptions + DiaryModel.WorkDescriptionModel(workCategory, workDescription)
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