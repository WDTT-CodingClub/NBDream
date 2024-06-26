package kr.co.main.calendar.screen.addScheduleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.calendar.CalendarDesignToken
import kr.co.main.calendar.common.AddScreenCenterTopAppBar
import kr.co.main.calendar.common.CalendarCategoryIndicator
import kr.co.main.calendar.common.CalendarContainerTextField
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo

@Composable
internal fun AddScheduleRoute(
    popBackStack: () -> Unit,
    viewModel: AddScheduleViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddScheduleScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        event = viewModel.event,
        popBackStack = popBackStack
    )
}

@Composable
private fun AddScheduleScreen(
    state: AddScheduleViewModel.AddScheduleScreenState,
    event: AddScheduleScreenEvent,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AddScreenCenterTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                screenMode = state.screenMode,
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
                ScheduleCategoryPicker(
                    modifier = Modifier.fillMaxWidth(),
                    calendarCrop = state.calendarCrop,
                    selectedType = state.scheduleType,
                    onTypeSelect = event::onTypeSelect
                )

                ScheduleTitleInput(
                    modifier = Modifier.fillMaxWidth(),
                    title = state.title,
                    onTitleInput = event::onTitleInput
                )

                // TODO 일정 정보 입력 UI 작성 중
//                ScheduleDateInput(
//                    modifier = Modifier.fillMaxWidth(),
//                    startDate = state.startDate,
//                    endDate = state.endDate,
//                    onStartDateSelect = event::onStartDateSelect
//                    onEndDateSelect = event::onEndDateSelect
//                )
            }
        }
    }
}

@Composable
private fun ScheduleCategoryPicker(
    calendarCrop: CropModel?,
    selectedType: ScheduleModelType,
    onTypeSelect: (ScheduleModelType) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var dropDownWidth by remember { mutableStateOf(0.dp) }
    var expandDropDown by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_schedule_header_category),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.graph1
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS))
                .background(MaterialTheme.colors.gray10)
                .apply {
                    if (calendarCrop != null) clickable { expandDropDown = true }
                }
                .onGloballyPositioned {
                    dropDownWidth = with(density) { it.size.width.toDp() }
                }
        ) {
            ScheduleCategoryPickerItem(
                scheduleType = selectedType,
                onTypeSelect = {}
            )
        }
        DropdownMenu(
            modifier = Modifier.width(dropDownWidth),
            expanded = expandDropDown,
            onDismissRequest = {
                expandDropDown = false
            }
        ) {
            Column(
                modifier = modifier
            ) {
                ScheduleCategoryPickerItem(
                    scheduleType = ScheduleModelType.All,
                    onTypeSelect = onTypeSelect
                )
                ScheduleCategoryPickerItem(
                    scheduleType = ScheduleModelType.Crop(calendarCrop!!),
                    onTypeSelect = onTypeSelect
                )
            }
        }
    }
}

@Composable
private fun ScheduleCategoryPickerItem(
    scheduleType: ScheduleModelType,
    onTypeSelect: (ScheduleModelType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onTypeSelect(scheduleType) }
    ) {
        CalendarCategoryIndicator(
            modifier = Modifier.padding(end = Paddings.medium),
            categoryColor = scheduleType.color
        )
        Text(
            text = stringResource(id = scheduleType.nameId),
            style = MaterialTheme.typo.body1,
            color = MaterialTheme.colors.gray1
        )
    }
}

@Composable
private fun ScheduleTitleInput(
    title: String,
    onTitleInput: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_schedule_header_title),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.graph1
        )
        CalendarContainerTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleInput,
            placeHolder = {
                Text(
                    text = stringResource(id = R.string.feature_main_calendar_add_schedule_input_hint_title),
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray4
                )
            }
        )
    }
}


