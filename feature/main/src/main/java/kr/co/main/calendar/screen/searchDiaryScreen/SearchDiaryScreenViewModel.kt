package kr.co.main.calendar.screen.searchDiaryScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kr.co.domain.usecase.calendar.SearchDiariesUseCase
import kr.co.main.mapper.calendar.DiaryModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface SearchDiaryScreenEvent {
    fun onQueryInput(query: String)
    fun onStartDateInput(startDate: LocalDate)
    fun onEndDateInput(endDate: LocalDate)
}

@HiltViewModel
internal class SearchDiaryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchDiaries: SearchDiariesUseCase
) : BaseViewModel<SearchDiaryScreenViewModel.SearchDiaryScreenState>(savedStateHandle),
    SearchDiaryScreenEvent {
    val event: SearchDiaryScreenEvent = this@SearchDiaryScreenViewModel

    private val _query = MutableStateFlow("")
    private val _startDate = MutableStateFlow(LocalDate.now())
    private val _endDate = MutableStateFlow(LocalDate.now())

    data class SearchDiaryScreenState(
        val calendarCrop: CropModel? = null,

        val query: String = "",
        val startDate: LocalDate = LocalDate.now(),
        val endDate: LocalDate = LocalDate.now(),
        val diaries: List<DiaryModel> = emptyList()
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
            select { it.query }.bindState(_query)
            select { it.startDate }.bindState(_startDate)
            select { it.endDate }.bindState(_endDate)
        }

        combine(_query, _startDate, _endDate) { query, startDate, endDate ->
            currentState.calendarCrop?.let { crop ->
                searchDiaries(
                    SearchDiariesUseCase.Params(
                        crop = crop.type.name,
                        query = query,
                        startDate = startDate,
                        endDate = endDate
                    )
                ).collect { diaries ->
                    updateState { copy(diaries = diaries.map { DiaryModelMapper.toRight(it) }) }
                }
            }
        }.launchIn(viewModelScopeEH)
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
}