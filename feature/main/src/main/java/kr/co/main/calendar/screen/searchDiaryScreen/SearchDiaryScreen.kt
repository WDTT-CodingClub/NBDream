package kr.co.main.calendar.screen.searchDiaryScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun SearchDiaryRoute(
    popBackStack: () -> Unit,
    viewModel: SearchDiaryScreenViewModel = hiltViewModel()
) {
    SearchDiaryScreen(
        modifier = Modifier.fillMaxSize(),
        state = viewModel.state.collectAsState(),
        event = viewModel.event,
        popBackStack = popBackStack
    )
}

@Composable
private fun SearchDiaryScreen(
    state: State<SearchDiaryScreenViewModel.SearchDiaryScreenState>,
    event: SearchDiaryScreenEvent,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
    }
}