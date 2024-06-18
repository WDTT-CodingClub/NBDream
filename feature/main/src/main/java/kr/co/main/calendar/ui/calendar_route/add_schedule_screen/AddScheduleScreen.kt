package kr.co.main.calendar.ui.calendar_route.add_schedule_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun AddScheduleScreen(
    //viewModel: AddScheduleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Text("AddScheduleScreen")
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

@Composable
private fun AddScheduleTopBar(
    onBackClick: () -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
}