package kr.co.main.calendar.screen.searchDiaryScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.SearchDiariesUseCase
import kr.co.main.mapper.calendar.DiaryModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.CalendarSortType
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface SearchDiaryScreenEvent {
    fun onQueryInput(query: String)
    fun onStartDateInput(startDate: LocalDate)
    fun onEndDateInput(endDate: LocalDate)
    fun onSortChange(sortType: CalendarSortType)
    fun onSearchClick()
}

@HiltViewModel
internal class SearchDiaryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchDiaries: SearchDiariesUseCase,
) : BaseViewModel<SearchDiaryScreenViewModel.SearchDiaryScreenState>(savedStateHandle),
    SearchDiaryScreenEvent {
    val event: SearchDiaryScreenEvent = this@SearchDiaryScreenViewModel

    private val _searchEvent = MutableSharedFlow<Unit>()

    private val _showToast = MutableSharedFlow<String>()
    val showToast = _showToast.asSharedFlow()

    data class SearchDiaryScreenState(
        val calendarCrop: CropModel? = null,

        val query: String = "",
        val sortType: CalendarSortType = CalendarSortType.RECENCY,

        val startDate: LocalDate = LocalDate.now().minusWeeks(1),
        val endDate: LocalDate = LocalDate.now(),
        val diaries: List<DiaryModel> = emptyList(),
    ) : State {
    }

    override fun createInitialState(savedState: Parcelable?): SearchDiaryScreenState =
        savedState?.let {
            // TODO "deserialize"
            SearchDiaryScreenState()
        } ?: SearchDiaryScreenState()

    init {
        with(savedStateHandle) {
            get<String>(CalendarNavGraph.ARG_CROP_NAME_ID)?.toIntOrNull()?.let { cropNameId ->
                updateState {
                    copy(calendarCrop = CropModel.create(CropModelType.ofValue(cropNameId)))
                }
            }
        }

        viewModelScopeEH.launch {
            combine(
                _searchEvent,
                state.select(SearchDiaryScreenState::startDate),
                state.select(SearchDiaryScreenState::endDate)
            ) {
                Unit,
                startDate,
                endDate,
                ->
                Triple(currentState.query, startDate, endDate)
            }.collect { diaries ->
                runCatching {
                    currentState.calendarCrop?.let { crop ->
                        SearchDiariesUseCase.Params(
                            crop = crop.type.name.lowercase(),
                            query = diaries.first,
                            startDate = diaries.second,
                            endDate = diaries.third
                        ).run {
                            searchDiaries(this)
                        }
                    }?.collect { diaries ->
                        updateState { copy(diaries = diaries.map(DiaryModelMapper::toRight)) }
                    }
                }.onFailure {
                    updateState { copy(diaries = emptyList()) }
                }
            }
        }
    }

    override fun onQueryInput(query: String) {
        updateState { copy(query = query) }
    }

    override fun onStartDateInput(startDate: LocalDate) {
        if (startDate > currentState.endDate) {
            viewModelScopeEH.launch {
                _showToast.emit("시작일은 종료일보다 작아야 합니다.")
            }
        } else {
            updateState { copy(startDate = startDate) }
        }
    }

    override fun onEndDateInput(endDate: LocalDate) {
        if (endDate < currentState.startDate) {
            viewModelScopeEH.launch {
                _showToast.emit("종료일은 시작일보다 커야 합니다.")
            }
        } else {
            updateState { copy(endDate = endDate) }
        }
    }

    override fun onSortChange(sortType: CalendarSortType) = updateState {
        copy(sortType = sortType)
    }

    override fun onSearchClick() {
        viewModelScopeEH.launch {
            _searchEvent.emit(Unit)
        }
    }
}