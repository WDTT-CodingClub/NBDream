package kr.co.main.calendar.ui.calendarScreen.searchDiaryScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.CropModelType
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface SearchDiaryScreenEvent {
    fun setCalendarCrop(crop: CropModel)

    fun onSearch(query: String, startDate: LocalDate, endDate: LocalDate)
}

@HiltViewModel
internal class SearchDiaryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    //private val searchDiaries: SearchDiariesUseCase
) : BaseViewModel<SearchDiaryScreenViewModel.SearchDiaryScreenState>(savedStateHandle),
    SearchDiaryScreenEvent {
    private val _query = MutableStateFlow("")
    private val _searchDateRange = MutableStateFlow(
        with(LocalDate.now()) {
            (LocalDate.of(year, monthValue, 1)..LocalDate.of(year, monthValue, lengthOfMonth()))
        },
    )

    data class SearchDiaryScreenState(
        val calendarCrop: CropModel? = null,
        val calendarYear: Int = LocalDate.now().year,
        val calendarMonth: Int = LocalDate.now().monthValue,
        val query: String = "",
        val searchDateRange: ClosedRange<LocalDate> = with(LocalDate.now()) {
            (LocalDate.of(year, monthValue, 1)..LocalDate.of(year, monthValue, lengthOfMonth()))
        },
        val diaries: List<DiaryModel> = emptyList()
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO "serialize"
            return null
        }
    }

    val event: SearchDiaryScreenEvent = this@SearchDiaryScreenViewModel

    init {
        with(savedStateHandle) {
            get<Int>("cropNameId")?.let { cropNameId ->
                updateState {
                    copy(calendarCrop = CropModel.create(CropModelType.ofValue(cropNameId)))
                }
            }
            get<Int>("year")?.let { year ->
                updateState { copy(calendarYear = year) }
            }
            get<Int>("month")?.let { month ->
                updateState { copy(calendarMonth = month) }
            }
        }

//        loadingScope {
//            currentState.calendarCrop?.let {
//                searchDiaries(
//                    SearchDiariesUseCase.Params(
//                        crop = CropEntity.getCropEntity(it.name).name.koreanName,
//                        query = currentState.query,
//                        startDate = currentState.searchDateRange.start.toString(),
//                        endDate = currentState.searchDateRange.endInclusive.toString()
//                    )
//                ).collect { diaries ->
//                    updateState {
//                        copy(diaries = diaries.map { diaryEntity ->
//                            DiaryModelMapper.toRight(diaryEntity)
//                        }
//                        )
//                    }
//                }
//            }
//        }
//
//        state.select { it.query }.bindState(_query)
//        state.select { it.searchDateRange }.bindState(_searchDateRange)
//        loadingScope {
//            currentState.calendarCrop?.let {
//                combine(_query, _searchDateRange) { query, searchDateRange ->
//                    searchDiaries(
//                        SearchDiariesUseCase.Params(
//                            crop = CropEntity.getCropEntity(it.name).name.koreanName,
//                            query = query,
//                            startDate = searchDateRange.start.toString(),
//                            endDate = searchDateRange.endInclusive.toString()
//                        )
//                    ).collect { diaries ->
//                        updateState {
//                            copy(diaries = diaries.map { diaryEntity ->
//                                DiaryModelMapper.toRight(diaryEntity)
//                            })
//                        }
//                    }
//                }
//            }
//        }
    }

    override fun createInitialState(savedState: Parcelable?): SearchDiaryScreenState =
        savedState?.let {
            // TODO "deserialize"
            SearchDiaryScreenState()
        } ?: SearchDiaryScreenState()

    override fun setCalendarCrop(crop: CropModel) {
        updateState {
            copy(calendarCrop = crop)
        }
    }

    override fun onSearch(query: String, startDate: LocalDate, endDate: LocalDate) {
        updateState {
            copy(query = query, searchDateRange = (startDate..endDate))
        }
    }
}