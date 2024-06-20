package kr.co.main.calendar.ui.calendarScreen.searchDiaryScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.calendar.model.CropModel


@Composable
internal fun SearchDiaryRoute(
    @StringRes calendarCropNameId: Int,
    calendarYear: Int,
    calendarMonth: Int,
    viewModel: SearchDiaryScreenViewModel = hiltViewModel()
) {
    SearchDiaryScreen(
        modifier = Modifier.fillMaxSize(),
        calendarCrop = CropModel.getCropModel(calendarCropNameId),
        calendarYear = calendarYear,
        calendarMonth = calendarMonth,
        state = viewModel.state.collectAsState(),
        event = viewModel.event
    )
}

@Composable
private fun SearchDiaryScreen(
    calendarCrop: CropModel,
    calendarYear: Int,
    calendarMonth: Int,
    state: State<SearchDiaryScreenViewModel.SearchDiaryScreenState>,
    event: SearchDiaryScreenEvent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {

    }
}