package kr.co.main.calendar.screen.searchDiaryScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.SearchDiariesUseCase
import kr.co.main.mapper.calendar.DiaryModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.CalendarSortType
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

internal interface SearchDiaryScreenEvent {
    fun onQueryInput(query: String)
    fun onStartDateInput(startDate: LocalDate)
    fun onEndDateInput(endDate: LocalDate)
    fun onSortChange(sortType: CalendarSortType)
}

@HiltViewModel
internal class SearchDiaryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchDiaries: SearchDiariesUseCase,
) : BaseViewModel<SearchDiaryScreenViewModel.SearchDiaryScreenState>(savedStateHandle),
    SearchDiaryScreenEvent {
    val event: SearchDiaryScreenEvent = this@SearchDiaryScreenViewModel

    private val _query = MutableStateFlow("")
    private val _startDate = MutableStateFlow(LocalDate.now())
    private val _endDate = MutableStateFlow(LocalDate.now())

    data class SearchDiaryScreenState(
        val calendarCrop: CropModel? = null,

        val query: String = "",
        val sortType: CalendarSortType = CalendarSortType.RECENCY,

        val startDate: LocalDate = LocalDate.now(),
        val endDate: LocalDate = LocalDate.now(),
        val diaries: List<DiaryModel> = emptyList(),
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO "serialize"
            return null
        }
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

        with(state) {
            select(SearchDiaryScreenState::query)
                .bindState(_query)
            select(SearchDiaryScreenState::startDate)
                .bindState(_startDate)
            select(SearchDiaryScreenState::endDate)
                .bindState(_endDate)
        }

        viewModelScopeEH.launch {
            combine(
                _query,
                _startDate,
                _endDate
            ) {
                    query,
                    startDate,
                    endDate,
                ->
                Timber.d("$query, $startDate, $endDate")
                currentState.calendarCrop?.let { crop ->
                    SearchDiariesUseCase.Params(
                        crop = crop.type.name,
                        query = query,
                        startDate = startDate,
                        endDate = endDate
                    ).run {
                        searchDiaries(this)
                    }
                }
            }.collect { diaries ->
                diaries?.collectLatest {
                    updateState { copy(diaries = it.map(DiaryModelMapper::toRight)) }
                }
            }
        }
    }

    override fun onQueryInput(query: String) {
        updateState { copy(query = query) }
    }

    override fun onStartDateInput(startDate: LocalDate) {
        updateState { copy(startDate = startDate) }
    }

    override fun onEndDateInput(endDate: LocalDate) {
        updateState { copy(endDate = endDate) }
    }

    override fun onSortChange(sortType: CalendarSortType) = updateState {
        copy(sortType = sortType)
    }
}