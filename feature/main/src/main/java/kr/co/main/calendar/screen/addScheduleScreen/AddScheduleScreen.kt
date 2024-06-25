package kr.co.main.calendar.screen.addScheduleScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.main.R
import kr.co.main.model.calendar.type.ScreenModeType
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

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
            AddScheduleTopBar(
                modifier = Modifier.fillMaxWidth(),
                screenMode = state.value.screenMode,
                popBackStack = popBackStack,
                onActionClick = event::onActionClick
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

            }
        }
    }
}

@Composable
private fun AddScheduleTopBar(
    screenMode: ScreenModeType,
    popBackStack: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DreamCenterTopAppBar(
        modifier = modifier,
        title = stringResource(id = R.string.feature_main_calendar_top_app_bar_add_schedule),
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    popBackStack()
                },
                imageVector = DreamIcon.Arrowleft,
                contentDescription = ""
            )
        },
        actions = {
            Text(
                modifier = Modifier.clickable {
                    onActionClick()
                },
                text = stringResource(
                    id = when (screenMode) {
                        ScreenModeType.POST_MODE -> R.string.feature_main_calendar_top_app_bar_post
                        ScreenModeType.EDIT_MODE -> R.string.feature_main_calendar_top_app_bar_edit
                    }
                ),
                style = MaterialTheme.typo.bodyM,
                color = MaterialTheme.colors.gray5
            )
        }
    )
}
