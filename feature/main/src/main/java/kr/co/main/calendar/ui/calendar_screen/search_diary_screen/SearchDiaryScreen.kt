package kr.co.main.calendar.ui.calendar_screen.search_diary_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun SearchDiaryRoute(
    viewModel: SearchDiaryScreenViewModel = hiltViewModel()
) {
    SearchDiaryScreen(
        modifier = Modifier.fillMaxSize()
        state = viewModel.state.collectAsState(),
        event = viewModel.event
    )
}

@Composable
private fun SearchDiaryScreen(
    state:
    event:
    modifier:Modifier = Modifier
)