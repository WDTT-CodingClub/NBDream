package kr.co.main.calendar.screen.addScheduleScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
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
import kr.co.main.calendar.common.CalendarDatePicker
import kr.co.main.calendar.common.TEXT_FIELD_LIMIT_MULTI
import kr.co.main.calendar.common.TEXT_FIELD_LIMIT_SINGLE
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.DropDown
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import timber.log.Timber
import java.time.LocalDate

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
    Timber.d("state: $state")

//    val enableAction by remember {
//        derivedStateOf { state.title.isNotEmpty() }
//    }
    val enableAction by remember(state.title) {
        mutableStateOf(state.title.isNotEmpty())
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        topBar = {
            AddScreenCenterTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                screenMode = state.screenMode,
                postModeTitleId = R.string.feature_main_calendar_top_app_bar_add_schedule,
                editModeTitleId = R.string.feature_main_calendar_top_app_bar_edit_schedule,
                actionHintId = R.string.feature_main_calendar_add_schedule_input_hint_title,
                enableAction = enableAction,
                popBackStack = popBackStack,
                onPostClick = event::onPostClick,
                onEditClick = event::onEditClick,
                onDeleteClick = event::onDeleteClick
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .navigationBarsPadding()
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.extra)
                    .verticalScroll(rememberScrollState())
            ) {
                ScheduleCategoryPicker(
                    modifier = Modifier.fillMaxWidth(),
                    calendarCrop = state.calendarCrop,
                    selectedType = state.scheduleType,
                    onTypeSelect = event::onTypeSelect
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                ScheduleTitleInput(
                    modifier = Modifier.fillMaxWidth(),
                    title = state.title,
                    onTitleInput = event::onTitleInput
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                ScheduleDatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    startDate = state.startDate,
                    endDate = state.endDate,
                    onStartDateSelect = event::onStartDateSelect,
                    onEndDateSelect = event::onEndDateSelect
                )
                Spacer(modifier = Modifier.height(Paddings.extra))
                ScheduleMemoInput(
                    modifier = Modifier.fillMaxWidth(),
                    memo = state.memo,
                    onMemoInput = event::onMemoInput
                )
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
    Timber.d("ScheduleDatePicker) calendarCrop: $calendarCrop, selectedType: $selectedType")

    val context = LocalContext.current
    val density = LocalDensity.current
    var dropDownWidth by remember { mutableStateOf(0.dp) }
    var expandDropDown by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_schedule_header_category),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CalendarDesignToken.INPUT_BOX_CORNER_RADIUS.dp))
                .background(MaterialTheme.colors.gray9)
                .clickable {
                    if (calendarCrop != null) {
                        expandDropDown = true
                    } else {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.feature_main_calendar_add_schedule_type_picker_toast),
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                }
                .onGloballyPositioned {
                    dropDownWidth = with(density) { it.size.width.toDp() }
                }
        ) {
            Row(
                modifier = modifier.padding(Paddings.xlarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CalendarCategoryIndicator(
                    modifier = Modifier.padding(end = Paddings.medium),
                    categoryColor = selectedType.color
                )
                Text(
                    text = stringResource(id = selectedType.nameId),
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray1
                )
            }
            if (calendarCrop != null) {
                Icon(
                    modifier = Modifier
                        .padding(end = Paddings.xlarge)
                        .align(Alignment.CenterEnd),
                    imageVector = DreamIcon.DropDown,
                    contentDescription = "",
                )
            }
        }
        DropdownMenu(
            modifier = Modifier
                .width(dropDownWidth)
                .background(Color.White),
            expanded = expandDropDown,
            onDismissRequest = {
                expandDropDown = false
            }
        ) {
            Column(
                modifier = modifier
            ) {
                ScheduleCategoryPickerItem(
                    modifier = Modifier.fillMaxWidth(),
                    scheduleType = ScheduleModelType.All,
                    onTypeSelect = {
                        onTypeSelect(it)
                        expandDropDown = false
                        // onDismissRequest()
                    }
                )
                if (calendarCrop != null) {
                    ScheduleCategoryPickerItem(
                        modifier = Modifier.fillMaxWidth(),
                        scheduleType = ScheduleModelType.Crop(calendarCrop),
                        onTypeSelect = {
                            onTypeSelect(it)
                            expandDropDown = false
                            // onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleCategoryPickerItem(
    scheduleType: ScheduleModelType,
    modifier: Modifier = Modifier,
    onTypeSelect: ((ScheduleModelType) -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .noRippleClickable {
                onTypeSelect?.invoke(scheduleType)
            }
            .padding(Paddings.medium),
        verticalAlignment = Alignment.CenterVertically
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
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_schedule_header_title),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarContainerTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                if (it.length >= TEXT_FIELD_LIMIT_SINGLE) {
                    Toast.makeText(
                        context,
                        context.getString(kr.co.nbdream.core.ui.R.string.core_ui_text_field_limit_single_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onTitleInput(it)
                }
            },
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

@Composable
private fun ScheduleDatePicker(
    startDate: LocalDate,
    endDate: LocalDate,
    onStartDateSelect: (LocalDate) -> Unit,
    onEndDateSelect: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_schedule_header_start_date),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarDatePicker(
            modifier = Modifier.fillMaxWidth(),
            date = startDate,
            onDateInput = onStartDateSelect
        )
        Spacer(modifier = Modifier.height(Paddings.extra))
        Text(
            text =
            stringResource(id = R.string.feature_main_calendar_add_schedule_header_end_date),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarDatePicker(
            modifier = Modifier.fillMaxWidth(),
            date = endDate,
            onDateInput = onEndDateSelect
        )
    }
}

@Composable
private fun ScheduleMemoInput(
    memo: String,
    onMemoInput: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.feature_main_calendar_add_schedule_header_memo),
            style = MaterialTheme.typo.h4,
            color = MaterialTheme.colors.gray1
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        CalendarContainerTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = memo,
            maxLines = Int.MAX_VALUE,
            onValueChange = {
                if (it.length >= TEXT_FIELD_LIMIT_MULTI) {
                    Toast.makeText(
                        context,
                        context.getString(kr.co.nbdream.core.ui.R.string.core_ui_text_field_limit_multi_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onMemoInput(it)
                }
            },
            placeHolder = {
                Text(
                    text = stringResource(id = R.string.feature_main_calendar_add_schedule_input_hint_memo),
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray4
                )
            }
        )
    }
}

