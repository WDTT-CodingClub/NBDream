package kr.co.main.calendar.ui.calendarScreen.addScheduleScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun AddScheduleRoute(
    viewModel: AddScheduleViewModel = hiltViewModel()
) {
    AddScheduleScreen(
        modifier = Modifier.fillMaxSize(),
        state = viewModel.state.collectAsState(),
        event = viewModel.event
    )
}

@Composable
private fun AddScheduleScreen(
    state: State<AddScheduleViewModel.AddScheduleScreenState>,
    event: AddScheduleScreenEvent,
    modifier: Modifier = Modifier
) {
    Text("AddScheduleScreen, crop: ${state.value.calendarCrop?.name ?: null}")
//    val addScheduleScreenState = viewModel.state.collectAsState()
//    val addScheduleScreenInput = viewModel.input
//
//    Scaffold(
//        modifier = modifier,
//        topBar = {
//            AddScheduleTopBar(
//                onBackClick = { /* TODO 전 화면으로 이동*/ },
//                onPostClick = addScheduleScreenInput::onPostClick
//            )
//        }
//    ) { innerPadding ->
//        Surface(
//            modifier = Modifier.padding(innerPadding)
//        ) {
//
//        }
//    }
}
