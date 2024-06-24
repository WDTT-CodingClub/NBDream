package kr.co.main.calendar.ui.screen.searchDiaryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun SearchDiaryRoute(
    viewModel: SearchDiaryScreenViewModel = hiltViewModel()
) {
    SearchDiaryScreen(
        modifier = Modifier.fillMaxSize(),
        state = viewModel.state.collectAsState(),
        event = viewModel.event
    )
}

@Composable
private fun SearchDiaryScreen(
    state: State<SearchDiaryScreenViewModel.SearchDiaryScreenState>,
    event: SearchDiaryScreenEvent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
    }
}