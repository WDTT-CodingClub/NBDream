package kr.co.main.calendar.ui.calendar_route.calendar_screen.schedule_tab

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ScheduleTab(
    modifier: Modifier = Modifier,
    viewModel: ScheduleTabViewModel = hiltViewModel()
) {
    Text(text = "ScheduleTab")
}