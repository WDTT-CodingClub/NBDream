package kr.co.main.calendar.screen.addScheduleScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.calendar.common.AddScreenCenterTopAppBar

@Composable
internal fun AddScheduleRoute(
    popBackStack: () -> Unit,
    viewModel: AddScheduleViewModel = hiltViewModel()
) {
    AddScheduleScreen(
        modifier = Modifier.fillMaxSize(),
        state = viewModel.state.collectAsState(),
        event = viewModel.event,
        popBackStack = popBackStack
    )
}

@Composable
private fun AddScheduleScreen(
    state: State<AddScheduleViewModel.AddScheduleScreenState>,
    event: AddScheduleScreenEvent,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AddScreenCenterTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                screenMode = state.value.screenMode,
                postModeTitleId = R.string.feature_main_calendar_top_app_bar_add_schedule,
                editModeTitleId = R.string.feature_main_calendar_top_app_bar_edit_schedule,
                popBackStack = popBackStack,
                onPostClick = event::onPostClick,
                onEditClick = event::onEditClick,
                onDeleteClick = event::onDeleteClick
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // TODO 일정 정보 입력 UI
            }
        }
    }
}
